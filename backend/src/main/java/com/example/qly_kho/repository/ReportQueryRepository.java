package com.example.qly_kho.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ReportQueryRepository {

    private static final LocalDate MIN_REPORT_DATE = LocalDate.of(1970, 1, 1);
    private static final LocalDate MAX_REPORT_DATE = LocalDate.of(9999, 12, 31);

    @PersistenceContext
    private EntityManager entityManager;

    public Object[] getOverview(LocalDate fromDate, LocalDate toDate) {
        LocalDate effectiveFromDate = normalizeFromDate(fromDate);
        LocalDate effectiveToDate = normalizeToDate(toDate);

        String sql = """
            SELECT
                COALESCE((
                    SELECT SUM(sod.quantity * sod.sale_price)
                    FROM sales_order_details sod
                    JOIN sales_orders so ON so.id = sod.sales_order_id
                    WHERE so.status = 'COMPLETED'
                      AND DATE(COALESCE(so.completed_at, so.created_at)) >= :fromDate
                      AND DATE(COALESCE(so.completed_at, so.created_at)) <= :toDate
                ), 0) AS revenue,
                COALESCE((
                    SELECT COUNT(*)
                    FROM sales_orders so
                    WHERE so.status = 'COMPLETED'
                      AND DATE(COALESCE(so.completed_at, so.created_at)) >= :fromDate
                      AND DATE(COALESCE(so.completed_at, so.created_at)) <= :toDate
                ), 0) AS total_orders,
                COALESCE((
                    SELECT SUM(iod.quantity * iod.import_price)
                    FROM import_order_details iod
                    JOIN import_orders io ON io.id = iod.import_order_id
                    WHERE io.status = 'COMPLETED'
                      AND DATE(COALESCE(io.completed_at, io.created_at)) >= :fromDate
                      AND DATE(COALESCE(io.completed_at, io.created_at)) <= :toDate
                ), 0) AS import_cost,
                COALESCE((
                    SELECT SUM(i.quantity * COALESCE(p.selling_price, 0))
                    FROM inventory i
                    JOIN products p ON p.id = i.product_id
                ), 0) AS inventory_value
            """;

        return (Object[]) entityManager.createNativeQuery(sql)
            .setParameter("fromDate", effectiveFromDate)
            .setParameter("toDate", effectiveToDate)
            .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getRevenueByDate(LocalDate fromDate, LocalDate toDate) {
        LocalDate effectiveFromDate = normalizeFromDate(fromDate);
        LocalDate effectiveToDate = normalizeToDate(toDate);

        String sql = """
            SELECT
                DATE(COALESCE(so.completed_at, so.created_at)) AS report_date,
                COALESCE(SUM(sod.quantity * sod.sale_price), 0) AS revenue
            FROM sales_order_details sod
            JOIN sales_orders so ON so.id = sod.sales_order_id
            WHERE so.status = 'COMPLETED'
              AND DATE(COALESCE(so.completed_at, so.created_at)) >= :fromDate
              AND DATE(COALESCE(so.completed_at, so.created_at)) <= :toDate
            GROUP BY DATE(COALESCE(so.completed_at, so.created_at))
            ORDER BY report_date
            """;

        return entityManager.createNativeQuery(sql)
            .setParameter("fromDate", effectiveFromDate)
            .setParameter("toDate", effectiveToDate)
            .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getTopProducts(LocalDate fromDate, LocalDate toDate, int limit) {
        LocalDate effectiveFromDate = normalizeFromDate(fromDate);
        LocalDate effectiveToDate = normalizeToDate(toDate);

        String sql = """
            SELECT
                p.name AS product_name,
                COALESCE(SUM(sod.quantity), 0) AS total_quantity
            FROM sales_order_details sod
            JOIN sales_orders so ON so.id = sod.sales_order_id
            JOIN product_batches pb ON pb.id = sod.product_batch_id
            JOIN products p ON p.id = pb.product_id
            WHERE so.status = 'COMPLETED'
              AND DATE(COALESCE(so.completed_at, so.created_at)) >= :fromDate
              AND DATE(COALESCE(so.completed_at, so.created_at)) <= :toDate
            GROUP BY p.id, p.name
            ORDER BY total_quantity DESC, p.name ASC
            LIMIT :limit
            """;

        return entityManager.createNativeQuery(sql)
            .setParameter("fromDate", effectiveFromDate)
            .setParameter("toDate", effectiveToDate)
            .setParameter("limit", limit)
            .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getInventorySummary(int limit) {
        String sql = """
            SELECT
                p.name AS product_name,
                COALESCE(i.quantity, 0) AS quantity
            FROM inventory i
            JOIN products p ON p.id = i.product_id
            ORDER BY i.quantity DESC, p.name ASC
            LIMIT :limit
            """;

        return entityManager.createNativeQuery(sql)
            .setParameter("limit", limit)
            .getResultList();
    }

    private LocalDate normalizeFromDate(LocalDate fromDate) {
        return fromDate != null ? fromDate : MIN_REPORT_DATE;
    }

    private LocalDate normalizeToDate(LocalDate toDate) {
        return toDate != null ? toDate : MAX_REPORT_DATE;
    }
}
