package com.example.taskapp.repository;

import com.example.taskapp.entity.Task;
import com.example.taskapp.entity.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository tasks;

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    @BeforeEach
    void setUp() {
        insertTestData();
    }

    private void insertTestData() {
        entityManager.createNativeQuery("""
        INSERT INTO users
        (id, name, email, department)
        VALUES
        (1, 'テストユーザー', 'test@example.com', '開発部')
        """)
                .executeUpdate();

        entityManager.createNativeQuery("""
        INSERT INTO users
        (id, name, email, department)
        VALUES
        (2, '別ユーザー', 'test2@example.com', '営業部')
        """)
                .executeUpdate();

        entityManager.createNativeQuery("""
        INSERT INTO tasks
        (title, description, user_id, status, due_date, created_at, updated_at)
        VALUES
        ('環境構築タスク', 'Dockerの環境を確認する', 1, 'TODO',
         '2026-12-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        """)
                .executeUpdate();

        entityManager.createNativeQuery("""
        INSERT INTO tasks
        (title, description, user_id, status, due_date, created_at, updated_at)
        VALUES
        ('API開発タスク', 'Spring BootのAPIを作成する', 1, 'DOING',
         '2027-01-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        """)
                .executeUpdate();

        entityManager.createNativeQuery("""
        INSERT INTO tasks
        (title, description, user_id, status, due_date, created_at, updated_at)
        VALUES
        ('資料作成タスク', '設計資料を作成する', 2, 'DONE',
         '2027-02-28', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        """)
                .executeUpdate();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void search_条件なしで全件取得できる() {
        List<Task> result
                = tasks.search("", null, null);

        assertEquals(3, result.size());
    }

    @Test
    void search_タイトルのキーワードで検索できる() {
        List<Task> result
                = tasks.search("環境", null, null);

        assertEquals(1, result.size());
        assertEquals("環境構築タスク", result.get(0).getTitle());
    }

    @Test
    void search_説明のキーワードで検索できる() {
        List<Task> result
                = tasks.search("Docker", null, null);

        assertEquals(1, result.size());
        assertEquals("環境構築タスク", result.get(0).getTitle());
    }

    @Test
    void search_ステータスで検索できる() {
        List<Task> result
                = tasks.search("", TaskStatus.DOING, null);

        assertEquals(1, result.size());
        assertEquals("API開発タスク", result.get(0).getTitle());
    }

    @Test
    void search_担当者で検索できる() {
        List<Task> result
                = tasks.search("", null, 1L);

        assertEquals(2, result.size());
    }

    @Test
    void search_複数条件で検索できる() {
        List<Task> result
                = tasks.search("API", TaskStatus.DOING, 1L);

        assertEquals(1, result.size());
        assertEquals("API開発タスク", result.get(0).getTitle());
    }

    @Test
    void search_該当データがない場合は空になる() {
        List<Task> result
                = tasks.search("存在しないタスク", null, null);

        assertTrue(result.isEmpty());
    }
}
