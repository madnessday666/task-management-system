package app.controller.task;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.repository.task.TaskSpecification;
import app.service.task.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    public static final String GET_TASKS = "/api/v1/tasks";
    public static final String CREATE_TASK = "/api/v1/tasks";
    public static final String UPDATE_TASK = "/api/v1/tasks";
    public static final String DELETE_TASK = "/api/v1/tasks";

    private final TaskServiceImpl taskService;

    @GetMapping(GET_TASKS)
    public Page<TaskEntity> getTasksByCreatorIdWithSpecs(TaskSearchFilterDto searchFilter,
                                                         @RequestParam int page,
                                                         @RequestParam int size){
        return taskService.getTaskPage(TaskSpecification.filterBy(searchFilter), PageRequest.of(page, size));
    }

    @PostMapping(CREATE_TASK)
    public CreateTaskResponse createTask(@RequestBody @Valid CreateTaskRequest createTaskRequest){
        return taskService.createTask(createTaskRequest);
    }

    @PatchMapping(UPDATE_TASK)
    public UpdateTaskResponse updateTask(@RequestBody @Valid UpdateTaskRequest updateTaskRequest){
       return taskService.updateTask(updateTaskRequest);
    }

    @DeleteMapping(DELETE_TASK)
    public DeleteTaskResponse deleteTask(@RequestBody @Valid DeleteTaskRequest deleteTaskRequest){
        return taskService.deleteTask(deleteTaskRequest);
    }

}
