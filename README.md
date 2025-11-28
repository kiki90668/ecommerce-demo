## 基本電商網站後端（Spring Boot + PostgreSQL + JWT)

這個專案是一個以 Spring Boot MVC 架構 實作的基本電商後端系統，包含：
- 使用者註冊 / 登入
- JWT Token 驗證
- 商品 CRUD
- 購物車
- 訂單
- 角色權限

目前完成的內容如以下：
## 使用者系統（含 JWT）
- 註冊（BCrypt 密碼加密）
- 登入取得 JWT Token
- 基於 Role 的權限管理
- UserDetails + JwtAuthFilter 認證流程
- 自訂 BizException + 統一回傳格式 ApiResponse

## 商品系統（Product）
- ADMIN新增、更新商品、刪除商品
- 所有使用者都能查詢商品（public）
- 價格使用 BigDecimal（PostgreSQL numeric）

##  購物車系統（Cart）
- 加入購物車
- 查詢個人購物車
- 刪除購物車項目

## 訂單系統（Order）
- 建立訂單
- 計算訂單金額（BigDecimal）
- 查詢個人訂單列表

## 角色權限
- ROLE_USER / ROLE_ADMIN
- SecurityConfig URL 授權控管
- Controller 使用：`@PreAuthorize`

---

# 系統架構

## 後端技術
- Java 21
- Spring Boot 3.5.7
- Spring Web (MVC)
- Spring Security（JWT + RBAC）
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## 資料庫
- PostgreSQL
- numeric(10, 2) 用於金額欄位
- JPA ORM 管理資料表

## 安全性
- JWT（HS256）
- BCrypt 密碼加密
- JwtAuthFilter（OncePerRequestFilter）
- 無狀態 Session（Stateless）
- @PreAuthorize 方法授權
