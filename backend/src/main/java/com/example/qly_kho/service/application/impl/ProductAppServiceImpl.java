package com.example.qly_kho.service.application.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.constant.ActionConstants;
import com.example.qly_kho.dto.request.product.ProductCreateRequest;
import com.example.qly_kho.dto.request.product.ProductSearchRequest;
import com.example.qly_kho.dto.request.product.ProductUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.product.ProductResponse;
import com.example.qly_kho.entity.Category;
import com.example.qly_kho.entity.Product;
import com.example.qly_kho.entity.ProductImage;
import com.example.qly_kho.entity.Unit;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.mapper.ProductMapper;
import com.example.qly_kho.service.application.CloudinaryService;
import com.example.qly_kho.service.application.ExcelExportService;
import com.example.qly_kho.service.application.ProductAppService;
import com.example.qly_kho.service.domain.ActivityLogService;
import com.example.qly_kho.service.domain.CategoryService;
import com.example.qly_kho.service.domain.ProductBatchService;
import com.example.qly_kho.service.domain.ProductImageService;
import com.example.qly_kho.service.domain.ProductService;
import com.example.qly_kho.service.domain.UnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductAppServiceImpl implements ProductAppService {

    private final CategoryService categoryService;
    private final UnitService unitService;
    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductBatchService productBatchService;
    private final CloudinaryService cloudinaryService;
    private final ActivityLogService activityLogService;
    private final ExcelExportService excelExportService;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request, String ip, Long actorUserId) {

        productService.existsBySku(request.sku());

        Category category = categoryService.findById(request.categoryId());
        Unit unit = unitService.findOrCreateByName(request.unit());


        Product product = Product.createProduct(
            request.sku(),
            request.name(),
            category,
            unit,
            request.description(),
            request.price()
        );
        if(Objects.equals(1, request.status())) product.enableProduct();
        else product.disableProduct();
        
        Product savedProduct = productService.saveProduct(product);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.CREATE, 
            ActionConstants.ENTITY_PRODUCT, 
            savedProduct.getId(), 
            "Product created successfully", 
            ip
        );

        if(request.image() != null) {

            Map<String, String> imageUrl = cloudinaryService.uploadImage(request.image());
            ProductImage image = ProductImage.create(
                savedProduct, 
                imageUrl.get("url"), 
                imageUrl.get("publicId"),
                0
            );
            image.changeToPrimary(true);

            ProductImage savedImage = productImageService.saveProductImage(image);

            activityLogService.logActivity(
                actorUserId, 
                ActionConstants.CREATE, 
                ActionConstants.ENTITY_PRODUCT_IMAGE, 
                savedImage.getId(), 
                "Product image created successfully", 
                ip
            );
        }

        return productMapper.toProductResponse(savedProduct);
    }
    
    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, String ip, Long actorUserId) {

        Product product = productService.findProductById(productId);

        if (product.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Product with id %d has already been deleted", productId)
            );
        }

        if(request.name() != null) product.changeName(request.name());
        if(request.description() != null) product.changeDescription(request.description());
        if(request.price() != null) product.changeSellingPrice(request.price());

        if(request.active() != null) {
            if(request.active()) product.enableProduct();
            else product.disableProduct();
        }

        if(request.categoryId() != null) {
            Category category = categoryService.findById(request.categoryId());
            product.changeCategory(category);
        }

        if(request.unit() != null) {
            Unit unit = unitService.findOrCreateByName(request.unit());
            product.changeUnit(unit);
        }

        Product savedProduct = productService.saveProduct(product);

        if(request.image() != null) {

            List<ProductImage> oldImage = productImageService.findByProductIdAndIsPrimaryIsTrue(product.getId());

            for(ProductImage image : oldImage) {

                try {

                    System.out.println("DELETE CLOUDINARY");

                    cloudinaryService.deleteImage(image.getAltText());

                    System.out.println("DELETE DB");

                    productImageService.deleteProductImage(image);

                    System.out.println("DONE");

                } catch (Exception e) {

                    e.printStackTrace();

                    throw e;
                }
            }

            Map<String, String> imageUrl = cloudinaryService.uploadImage(request.image());
            ProductImage image = ProductImage.create(
                savedProduct, 
                imageUrl.get("url"), 
                imageUrl.get("publicId"),
                0
            );
            image.changeToPrimary(true);

            ProductImage savedImage = productImageService.saveProductImage(image);

            activityLogService.logActivity(
                actorUserId, 
                ActionConstants.UPDATE, 
                ActionConstants.ENTITY_PRODUCT_IMAGE, 
                savedImage.getId(), 
                "Product image created successfully", 
                ip
            );

        }

        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId, String ip, Long actorUserId) {
       
        Product product = productService.findProductById(productId);

        if (product.getDeletedAt() != null) {
            throw new DuplicateException(
                String.format("Product with id %d has already been deleted", productId)
            );
        }

        productBatchService.existsByProductId(productId);

        productService.deleteProduct(product);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.DELETE, 
            ActionConstants.ENTITY_PRODUCT, 
            product.getId(), 
            "Product deleted successfully", 
            ip
        );
    }

    
    @Override
    public PageResponse<ProductResponse> getProductList(ProductSearchRequest request) {

        int page = request.page();
        int size = request.size();
        String keyword = request.keyword();

        PageRequest pageable = PageRequest.of(
            page, 
            size,
            Sort.by("deletedAt").descending()
        );

        Page<Product> productPage = productService.searchProduct(keyword, pageable);

        List<ProductResponse> data = productPage.getContent()
                                        .stream()
                                        .map(p -> productMapper.toProductResponse(p))
                                        .toList();

        return new PageResponse<>(
            data,
            productPage.getNumber(),
            productPage.getSize(),
            productPage.getTotalElements(),
            productPage.getTotalPages()
        );
    }

    @Override
    public ProductResponse getProduct(Long productId) {

        Product product = productService.findProductById(productId);
        
        return productMapper.toProductResponse(product);
    }

    @Override
    public byte[] excelTableExport(ProductSearchRequest request) {

        String keyword = request.keyword();

        List<Product> data = productService.getAllForExport(keyword);

        String[] headers = {"Sản phẩm", "SKU", "Loại linh kiện", "Đơn vị", "Giá bán", "Trạng thái", "Ngày tạo", "Ngày cập nhật", "Ngày xóa"};

        List<Function<Product, Object>> fieldMappers = List.of(
            product -> product.getName(),
            product -> product.getSku(),
            product -> product.getCategory().getName(),
            product -> product.getUnit().getName(),
            product -> product.getSellingPrice(),
            product -> product.isActive() ? "Hoaạt động" : "Khóa",
            product -> product.getCreatedAt(),
            product -> product.getUpdatedAt(),
            product -> product.getDeletedAt()
        );

        return excelExportService.exportTableExcel(
            data,
            "product",
            "Danh sách sản phẩm",
            headers,
            fieldMappers
        );
    }
}
