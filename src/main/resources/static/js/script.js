function getFormattedDate(createdDate) {
    return createdDate.substring(0, 10) + '---' + createdDate.substring(11, 19);
}

function handleProjects(response, callback) {
    $('#projects').empty()
    $('.pagination').empty()
    response.content.map(project => {
        let project_elem = $(`
            <a href="/projects/${project.id}" class="topic_item">
                <h3>Title: ${project.name}</h3>
                <h4>Created date: ${project.createdAt}</h4>
                <p>Author: ${project.username}</p>
                <span>Tasks: ${project.tasks.length}</span>
            </a>
        `)
        $('#projects').append(project_elem)
    })

    handlePagination(response, callback)
}

function getAllProjects(pageNumber = 0) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/projects?page=' + pageNumber,
        success: (response) => {
            handleProjects(response, (i) => getAllProjects(i))
        }
    })
}

function getAllSearchedProjects(pageNumber = 0, query = "") {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/projects/search?query=${query}&page=${pageNumber}`,
        success: (response) => {
            handleProjects(response, (i) => getAllSearchedProjects(i, query))
        }
    })
}

function onSearchFormSubmit() {
    $('#search_project_form').submit(function (e) {
        e.preventDefault()
        getAllSearchedProjects(0, $('#search_project_input').val())
    })
}

function handleProjectItem(response) {
    $('.topic_item_header h3').text(`Title: ${response.name}`)
    $('.topic_item_header p').text(`Created at: ${response.createdAt}`)
    $('.topic_item_header span').text(`Author: ${response.username}`)
    $('.project_item_desc_details').append($(`<div class="d-flex align-items-center"><p>Title: <span class="project_none_fields">${response.name}</span></p><input class="d-none" type="text" id="project_name_input" value="${response.name}" required></div>`))
    $('.project_item_desc_details').append($(`<div class="d-flex align-items-center"><p>Description: <span class="project_none_fields">${response.description}</span></p><input class="d-none" type="text" id="project_desc_input" value="${response.description}" required></div>`))
    $('.project_item_desc_details').append($(`<div class="d-flex align-items-center"><p>Status: <span class="project_none_fields">${response.status}</span></p><select class="d-none" id="project_status_input">
    ${"NotStarted" == response.status ? "<option selected value=\"NotStarted\">NotStarted</option>" : "<option value=\"NotStarted\">NotStarted</option>"}
    ${"Active" == response.status ? "<option selected value=\"Active\">Active</option>" : "<option value=\"Active\">Active</option>"}
    ${"Completed" == response.status ? "<option selected value=\"Completed\">Completed</option>" : "<option value=\"Completed\">Completed</option>"}
    </select></div>`))
    $('.project_item_desc_details').append($(`<div class="d-flex align-items-center"><p>Priority: <span class="project_none_fields">${response.priority}</span></p><select class="d-none" id="project_priority_input">
     ${1 == response.priority ? "<option selected value=\"1\">1</option>" : "<option value=\"1\">1</option>"}
     ${2 == response.priority ? "<option selected value=\"2\">2</option>" : "<option value=\"2\">2</option>"}
     ${3 == response.priority ? "<option selected value=\"3\">3</option>" : "<option value=\"3\">3</option>"}
     ${4 == response.priority ? "<option selected value=\"4\">4</option>" : "<option value=\"4\">4</option>"}
     ${5 == response.priority ? "<option selected value=\"5\">5</option>" : "<option value=\"5\">5</option>"}
    </select></div>`))
    $('.project_item_desc_details').append($(`<div class="d-flex align-items-center"><p>Completion date: <span class="project_none_fields">${response.completedAt}</span></p><input class="d-none" type="date" id="project_completionDate_input" value="${response.completedAt}" required></div>`))
}

function handlePagination(response, callback) {
    if (!response.empty) {
        for (let i = 0; i < response.totalPages; i++) {
            let pagination_item = $(`<li class="page-item"><a class="page-link" href="#">${i + 1}</a></li>`)
            pagination_item.click(function (e) {
                e.preventDefault()
                callback(i)
            })
            if (response.number == i) {
                pagination_item.addClass('active')
            }
            $('.pagination').append(pagination_item)
        }
    }
}

function handleRateTask(liked, taskId, pageNumber, callback) {
    var _csrf_header = $('meta[name=_csrf_header]').attr('content');
    var _csrf_token = $('meta[name=_csrf_token]').attr('content');
    $.ajax({
        method: 'POST',
        url: 'http://localhost:8080/api/tasks/rates/' + liked,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(_csrf_header, _csrf_token);
        },
        data: {
            taskId: taskId
        },
        success: () => {
            callback(pageNumber)
        }
    })
}

function handleTasks(response, callback, projectId, userEmail) {
    $('.project_item_tasks').empty()
    $('.pagination').empty()
    response.content.map(task => {
        let hasLiked;
        let hasDisliked;
        let likesCount = 0;
        let dislikesCount = 0;
        if (userEmail != null) {
            task.taskRates.map(r => {
                if (r.userEmail == userEmail && r.liked == true) {
                    hasLiked = true;
                }
                if (r.userEmail == userEmail && r.disliked == true) {
                    hasDisliked = true;
                }
                if (r.liked) {
                    likesCount++
                }
                if (r.disliked) {
                    dislikesCount++
                }
            })
        }

        let like = $(`<div class="d-flex flex-column align-items-center mr-4"><i style="font-weight: bold; cursor:pointer;">like</i><span>${likesCount}</span></div>`)
        let dislike = $(`<div class="d-flex flex-column align-items-center"><i style="font-weight: bold; cursor:pointer;">dislike</i><span>${dislikesCount}</span></div>`)
        like.click(() => {
            handleRateTask("liked", task.id, response.number, (pageNumber) => callback(projectId, pageNumber, userEmail))
        })
        dislike.click(() => {
            handleRateTask("disliked", task.id, response.number, (pageNumber) => callback(projectId, pageNumber, userEmail))
        })
        if (hasLiked) {
            like.find('i').css('color', 'red')
        }
        if (hasDisliked) {
            dislike.find('i').css('color', 'red')
        }
        let task_item = $(`
               <div class="topic_item_answer d-flex align-items-center my-4">
                    <div> 
                        <div class="topic_item_answer_header mb-2 d-flex align-items-center">
                            <h4>${task.username}</h4>
                            <span>${getFormattedDate(task.createdAt)}</span>
                        </div>
                        <div class="d-flex align-items-center">
                            <p>Title: <span class="task_none">${task.name}</span></p>
                            <input class="task_name_input d-none" value="${task.name}" required>
                        </div>
                        <div class="d-flex align-items-center">
                            <p>Description: <span class="task_none">${task.description}</span></p>
                            <input class="task_desc_input d-none" value="${task.description}" required>
                        </div>
                        <div class="d-flex align-items-center"><p>Status: <span class="task_none">${task.status}</span></p><select class="task_status_input d-none" >
                        ${"ToDo" == task.status ? "<option selected value=\"ToDo\">ToDo</option>" : "<option value=\"ToDo\">ToDo</option>"}
                        ${"InProgress" == task.status ? "<option selected value=\"InProgress\">InProgress</option>" : "<option value=\"InProgress\">InProgress</option>"}
                        ${"Done" == task.status ? "<option selected value=\"Done\">Done</option>" : "<option value=\"Done\">Done</option>"}
                        </select></div>
                        <div class="d-flex align-items-center"><p>Priority: <span class="task_none">${task.priority}</span></p><select class="task_priority_input d-none">
                        ${1 == task.priority ? "<option selected value=\"1\">1</option>" : "<option value=\"1\">1</option>"}
                        ${2 == task.priority ? "<option selected value=\"2\">2</option>" : "<option value=\"2\">2</option>"}
                        ${3 == task.priority ? "<option selected value=\"3\">3</option>" : "<option value=\"3\">3</option>"}
                        ${4 == task.priority ? "<option selected value=\"4\">4</option>" : "<option value=\"4\">4</option>"}
                        ${5 == task.priority ? "<option selected value=\"5\">5</option>" : "<option value=\"5\">5</option>"}
                        </select></div>
                        <div class="topic_item_answer_rates d-flex mb-3"></div>
                        <button id="" class="btn btn-warning edit_task_btn" data-value="false">Edit Task</button>
                        <button id="" class="btn btn-danger ml-2 delete_task_btn">Delete Task</button>
                    </div>
                </div>
        `)
        task_item.find('.edit_task_btn').click(function () {
            onEditTaskBtnClick(task.id, projectId, task_item)
        })
        task_item.find('.delete_task_btn').click(function () {
            onDeleteTaskBtnClick(task.id, projectId)
        })
        task_item.find('.topic_item_answer_rates').append(like, dislike);
        $('.project_item_tasks').append(task_item)
    })

    handlePagination(response, (pageNumber) => callback(projectId, pageNumber, userEmail))

}

function getTasksByProjectId(projectId, pageNumber = 0, userEmail = null) {
    $.ajax({
        method: "GET",
        url: 'http://localhost:8080/api/tasks/' + projectId + '?page=' + pageNumber,
        success: function (data) {
            handleTasks(data, (projectId, pageNumber) => getTasksByProjectId(projectId, pageNumber, userEmail), projectId, userEmail)
        }
    });
}

function getProjectItem(id, userEmail = null) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/projects/' + id,
        success: (response) => {
            handleProjectItem(response)
            getTasksByProjectId(response.id, 0, userEmail)
        },
        error: (error) => {
            $('.project').empty()
            $('.project_not_found').append($(`<h3 class="text-center">${error.responseText}</h3>`))
        }
    })
}

function onAddTaskFormSubmit(userEmail = null) {
    $('#task_form').submit(function (e) {
        e.preventDefault()
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');
        const formData = new FormData(document.getElementById('task_form'))

        let form = {
            projectId: formData.get("projectId"),
            name: formData.get("name"),
            description: formData.get("description"),
            priority: formData.get("priority")
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/api/tasks',
            data: form,
            success: () => {
                $('#exampleModalCenter').modal('hide')
                $('#task_name').val("")
                $('#task_desc').val("")
                $('.task_form_warnings').empty()
                getTasksByProjectId(formData.get("projectId"), 0, userEmail)
            },
            error: (response) => {
                $('.task_form_warnings').empty()
                response['responseJSON'].map(message => {
                    let p = $(`<p class="warning_message">${message}</p>`)
                    $('.task_form_warnings').append(p)
                })
            }
        })
    })
}

function onAddProjectFormSubmit() {
    $('#add_project_form').submit(function (e) {
        e.preventDefault()
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');
        const formData = new FormData(document.getElementById('add_project_form'))

        let form = {
            name: formData.get("name"),
            description: formData.get("description"),
            completedAt: formData.get("completedAt"),
            priority: formData.get("priority"),
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/api/projects',
            data: form,
            success: () => {
                window.location.replace("http://localhost:8080");
            },
            error: (response) => {
                $('.add_project_warnings').empty()
                response['responseJSON'].map(message => {
                    let p = $(`<p class="warning_message">${message}</p>`)
                    $('.add_project_warnings').append(p)
                })
            }
        })
    })
}

function onDeleteProjectBtnClick(projectId) {
    var _csrf_header = $('meta[name=_csrf_header]').attr('content');
    var _csrf_token = $('meta[name=_csrf_token]').attr('content');

    $.ajax({
        method: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(_csrf_header, _csrf_token);
        },
        url: 'http://localhost:8080/api/projects/delete',
        data: {
            projectId: projectId
        },
        success: () => {
            window.location.replace("http://localhost:8080");
        },
        error: (response) => {
            alert(response)
        }
    })
}

function onEditProjectBtnClick(projectId) {
    let isTrue = ($('#edit_project_btn').attr('data-value') === 'true')
    if (!isTrue) {
        $('#edit_project_btn').attr('data-value', 'true')
        $('.project_none_fields').hide()
        $('#project_name_input').removeClass('d-none')
        $('#project_status_input').removeClass('d-none')
        $('#project_desc_input').removeClass('d-none')
        $('#project_completionDate_input').removeClass('d-none')
        $('#project_priority_input').removeClass('d-none')
    } else {
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');

        let form = {
            id: projectId,
            status: $('#project_status_input').val(),
            name: $('#project_name_input').val(),
            description: $('#project_desc_input').val(),
            completedAt: $('#project_completionDate_input').val(),
            priority: $('#project_priority_input').val(),
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/api/projects/edit',
            data: form,
            success: () => {
                window.location.replace("http://localhost:8080/projects/" + projectId);
            },
            error: (response) => {
                alert(response['responseJSON'][0])
            }
        })
    }

}

function onDeleteTaskBtnClick(taskId, projectId) {
    var _csrf_header = $('meta[name=_csrf_header]').attr('content');
    var _csrf_token = $('meta[name=_csrf_token]').attr('content');

    $.ajax({
        method: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(_csrf_header, _csrf_token);
        },
        url: 'http://localhost:8080/api/tasks/delete',
        data: {
            taskId: taskId
        },
        success: () => {
            window.location.replace("http://localhost:8080/projects/" + projectId);
        },
        error: (response) => {
            alert(response)
        }
    })
}


function onEditTaskBtnClick(taskId, projectId, task) {
    let isTrue = (task.find('.edit_task_btn').attr('data-value') === 'true')
    if (!isTrue) {
        task.find('.edit_task_btn').attr('data-value', 'true')
        task.find('.task_none').hide()
        task.find('.task_name_input').removeClass('d-none')
        task.find('.task_status_input').removeClass('d-none')
        task.find('.task_desc_input').removeClass('d-none')
        task.find('.task_priority_input').removeClass('d-none')
    } else {
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');

        let form = {
            id: taskId,
            status: task.find('.task_status_input').val(),
            name: task.find('.task_name_input').val(),
            description: task.find('.task_desc_input').val(),
            priority: task.find('.task_priority_input').val(),
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/api/tasks/edit',
            data: form,
            success: () => {
                window.location.replace("http://localhost:8080/projects/" + projectId);
            },
            error: (response) => {
                alert(response['responseJSON'][0])
            }
        })
    }

}