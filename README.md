# IK Webアプリ開発課題・完成版

レビュー者が保持するマスター完成版です。

## 構成
- frontend: React + TypeScript + Vite
- backend: Java 21 + Spring Boot + Spring Data JPA
- database: PostgreSQL
- environment: Docker Compose

## 起動
1. `docker compose up -d`
2. `cd backend` → `mvn spring-boot:run`
3. 別ターミナルで `cd frontend` → `npm install` → `npm run dev`

Frontend: http://localhost:5173
Backend health: http://localhost:8080/api/health

DB: task_management / task_user / task_password / 5432
