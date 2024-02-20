# 코드 빌드 및 어플리케이션 실행
- 빌드
```bash 
gradle build
```
- 실행
```bash
gradle bootRun
```

# 테스트/실행 방법
## 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
```bash 
curl -X 'GET' \
 'localhost:8080/price/minPriceProducts'
```

## 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
```bash 
curl -X 'GET' \
 'localhost:8080/price/minPriceBrandInfo'
```

## 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
```bash 
curl -X 'GET' \
 'localhost:8080/price/minMaxPriceProductInfo/{카테고리명}'
```

## 상품 추가 API
```bash
curl -X 'POST' 'localhost:8080/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "categoryName" : "상의",
    "brand" : "A",
    "price" : 9000
}'
```

## 상품 가격 변경 API
```bash
curl -X 'POST' 'localhost:8080/product/updatePrice' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "price" : 15000
}'
```

## 상품 삭제 API
```bash
curl -X 'POST' 'localhost:8080/product/remove' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'
```

## 브랜드 추가 API
```bash
curl -X 'POST' 'localhost:8080/brand/add' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "J"
}'
```

## 브랜드 수정 API
```bash
curl -X 'POST' 'localhost:8080/brand/add' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "J"
}'
```

## 브랜드 삭제 API
```bash
curl -X 'POST' 'localhost:8080/brand/remove' \
--header 'Content-Type: application/json' \
--data '{
    "id" : 1
}'
```