package com.example.taskapp.service;

import com.example.taskapp.dto.SummaryResponse;
import com.example.taskapp.dto.TaskRequest;
import com.example.taskapp.dto.TaskResponse;
import com.example.taskapp.entity.Task;
import com.example.taskapp.entity.TaskStatus;
import com.example.taskapp.entity.User;
import com.example.taskapp.exception.NotFoundException;
import com.example.taskapp.repository.TaskRepository;
import com.example.taskapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository tasks;
    @Mock
    private UserRepository users;
    @InjectMocks
    private TaskService service;

    private User createUser() {
        User user = mock(User.class);
        lenient().when(user.getId()).thenReturn(1L);
        lenient().when(user.getName()).thenReturn("テストユーザー");
        lenient().when(user.getEmail()).thenReturn("test@example.com");
        lenient().when(user.getDepartment()).thenReturn("開発部");
        return user;
    }

    private Task createTask() {
        Task task = mock(Task.class);
        User user = createUser();
        lenient().when(task.getId()).thenReturn(1L);
        lenient().when(task.getTitle()).thenReturn("テストタスク");
        lenient().when(task.getDescription()).thenReturn("テスト説明");
        lenient().when(task.getUser()).thenReturn(user);
        lenient().when(task.getStatus()).thenReturn(TaskStatus.TODO);
        lenient().when(task.getDueDate())
                .thenReturn(LocalDate.of(2026, 12, 31));
        return task;
    }

    @Test
    void findAll_条件なしでタスク一覧を取得できる() {
        Task task = createTask();
        when(tasks.search("", null, null))
                .thenReturn(List.of(task));
        List<TaskResponse> result
                = service.findAll(null, null, null);
        assertEquals(1, result.size());
        assertEquals("テストタスク", result.get(0).title());
        verify(tasks).search("", null, null);
    }

    @Test
    void findAll_キーワードで検索できる() {
        Task task = createTask();

        when(tasks.search("テスト", null, null))
                .thenReturn(List.of(task));

        List<TaskResponse> result
                = service.findAll("テスト", null, null);

        assertEquals(1, result.size());
        assertEquals("テストタスク", result.get(0).title());

        verify(tasks).search("テスト", null, null);
    }

    @Test
    void findAll_ステータスで検索できる() {
        Task task = createTask();

        when(tasks.search("", TaskStatus.TODO, null))
                .thenReturn(List.of(task));

        List<TaskResponse> result
                = service.findAll(null, "TODO", null);

        assertEquals(1, result.size());
        assertEquals("TODO", result.get(0).status());

        verify(tasks).search("", TaskStatus.TODO, null);
    }

    @Test
    void findAll_担当者で検索できる() {
        Task task = createTask();

        when(tasks.search("", null, 1L))
                .thenReturn(List.of(task));

        List<TaskResponse> result
                = service.findAll(null, null, 1L);

        assertEquals(1, result.size());

        verify(tasks).search("", null, 1L);
    }

    @Test
    void findAll_複数条件で検索できる() {
        Task task = createTask();

        when(tasks.search(
                "テスト",
                TaskStatus.TODO,
                1L
        )).thenReturn(List.of(task));

        List<TaskResponse> result
                = service.findAll("テスト", "TODO", 1L);

        assertEquals(1, result.size());

        verify(tasks).search(
                "テスト",
                TaskStatus.TODO,
                1L
        );
    }

    @Test
    void findById_存在するIDのタスクを取得できる() {
        Task task = createTask();

        when(tasks.findById(1L))
                .thenReturn(Optional.of(task));

        TaskResponse result
                = service.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("テストタスク", result.title());

        verify(tasks).findById(1L);
    }

    @Test
    void findById_存在しないIDの場合は例外になる() {
        when(tasks.findById(99999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(99999L)
        );

        verify(tasks).findById(99999L);
    }

    @Test
    void summary_タスク件数を取得できる() {
        when(tasks.count()).thenReturn(10L);

        when(tasks.countByStatus(TaskStatus.TODO))
                .thenReturn(3L);

        when(tasks.countByStatus(TaskStatus.DOING))
                .thenReturn(4L);

        when(tasks.countByStatus(TaskStatus.DONE))
                .thenReturn(3L);

        SummaryResponse result = service.summary();

        assertEquals(10L, result.total());
        assertEquals(3L, result.todo());
        assertEquals(4L, result.doing());
        assertEquals(3L, result.done());
    }

    @Test
    void create_タスクを登録できる() {
        User user = createUser();
        Task savedTask = createTask();

        TaskRequest request = mock(TaskRequest.class);

        when(request.getTitle())
                .thenReturn("新規タスク");

        when(request.getDescription())
                .thenReturn("新規タスクの説明");

        when(request.getUserId())
                .thenReturn(1L);

        when(request.getStatus())
                .thenReturn(TaskStatus.TODO);

        when(request.getDueDate())
                .thenReturn(LocalDate.of(2026, 12, 31));

        when(users.findById(1L))
                .thenReturn(Optional.of(user));

        when(tasks.save(any(Task.class)))
                .thenReturn(savedTask);

        TaskResponse result
                = service.create(request);

        assertEquals("テストタスク", result.title());

        verify(users).findById(1L);
        verify(tasks).save(any(Task.class));
    }

    @Test
    void create_存在しない担当者の場合はエラーになる() {
        TaskRequest request = mock(TaskRequest.class);

        when(request.getUserId())
                .thenReturn(99999L);

        when(users.findById(99999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request)
        );

        verify(tasks, never()).save(any(Task.class));
    }

    @Test
    void update_タスクを更新できる() {
        Task task = createTask();
        User user = createUser();

        TaskRequest request = mock(TaskRequest.class);

        when(request.getTitle())
                .thenReturn("更新後タスク");

        when(request.getDescription())
                .thenReturn("更新後の説明");

        when(request.getUserId())
                .thenReturn(1L);

        when(request.getStatus())
                .thenReturn(TaskStatus.DOING);

        when(request.getDueDate())
                .thenReturn(LocalDate.of(2027, 1, 31));

        when(tasks.findById(1L))
                .thenReturn(Optional.of(task));

        when(users.findById(1L))
                .thenReturn(Optional.of(user));

        when(tasks.save(task))
                .thenReturn(task);

        TaskResponse result
                = service.update(1L, request);

        assertEquals("テストタスク", result.title());

        verify(tasks).findById(1L);
        verify(users).findById(1L);
        verify(tasks).save(task);

        verify(task).setTitle("更新後タスク");
        verify(task).setDescription("更新後の説明");
        verify(task).setUser(user);
        verify(task).setStatus(TaskStatus.DOING);
        verify(task).setDueDate(
                LocalDate.of(2027, 1, 31)
        );
    }

    @Test
    void update_存在しないタスクIDの場合は例外になる() {
        TaskRequest request = mock(TaskRequest.class);

        when(tasks.findById(99999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.update(99999L, request)
        );

        verify(tasks, never()).save(any(Task.class));
    }

    @Test
    void update_存在しない担当者の場合はエラーになる() {
        Task task = createTask();

        TaskRequest request = mock(TaskRequest.class);

        when(request.getUserId())
                .thenReturn(99999L);

        when(tasks.findById(1L))
                .thenReturn(Optional.of(task));

        when(users.findById(99999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.update(1L, request)
        );

        verify(tasks, never()).save(any(Task.class));
    }

    @Test
    void delete_タスクを削除できる() {
        Task task = createTask();

        when(tasks.findById(1L))
                .thenReturn(Optional.of(task));

        service.delete(1L);

        verify(tasks).findById(1L);
        verify(tasks).delete(task);
    }

    @Test
    void delete_存在しないタスクIDの場合は例外になる() {
        when(tasks.findById(99999L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.delete(99999L)
        );

        verify(tasks, never()).delete(any(Task.class));
    }
}
