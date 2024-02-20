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

## - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
```bash 
curl -X 'GET' \
 'localhost:8080/price/minPriceBrandInfo'
```