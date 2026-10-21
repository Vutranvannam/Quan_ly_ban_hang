FROM python:3.11-slim

WORKDIR /app

# cài thư viện hệ thống (nếu dùng PostgreSQL)
RUN apt-get update && apt-get install -y libpq-dev gcc

# cài python dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# copy toàn bộ scripts vào container
COPY . .