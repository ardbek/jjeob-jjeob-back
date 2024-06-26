# jjeob - core module

## Core Module

### 개요
코어 모듈은 애플리케이션의 모든 비즈니스 로직, 데이터 접근 및 데이터 모델 관리를 처리합니다. API 모듈과 데이터베이스 사이의 중개자 역할을 합니다.

### 구성 요소
- **Service**: 애플리케이션에 필요한 비즈니스 로직을 구현합니다.
- **Entity**: 데이터베이스 모델을 나타내는 JPA 엔티티입니다.
- **Repository**: 데이터베이스 엔티티에 대한 CRUD 작업을 처리하는 Spring Data JPA 리포지토리입니다.

### 사용법
이 모듈은 API 모듈 및 비즈니스 로직이 필요한 다른 모듈에 의해 사용됩니다.

### 의존성
- **Common Module**: 필요한 경우 공유 유틸리티와 공통 기능에 접근할 수 있습니다.
