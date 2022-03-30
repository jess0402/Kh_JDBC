-------------------------------------------------------------------------------------------
-- 관리자계정
-------------------------------------------------------------------------------------------
-- student계정 생성
-- c## 접두어 사용안함 
alter session set "_oracle_script" = true;

create user student  -- 계정이름 student 
identified by student -- 비밀번호 student
default tablespace users;  -- 실제 테이블이 저장될 공간 users 

grant connect, resource to student; 

alter user student quota unlimited on users; -- users에 대한 권한을 무한히 주겠다. 



