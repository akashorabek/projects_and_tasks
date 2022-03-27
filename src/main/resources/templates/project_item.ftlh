<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>About project</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
</head>
<body>
<@p.header />


<div class="container">
    <div class="project_not_found"></div>
   <div class="project">
        <div class="topic_item_header mb-4 d-flex justify-content-between align-items-center">
            <h3></h3>
            <p></p>
            <span></span>
        </div>
       <div class="topic_item_desc">
            <div class="topic_item_desc_inner">
                <h3>Project content: </h3>
                <div class="project_item_desc_details mb-3">

                </div>
                <@security.authorize access="isAuthenticated()">
                    <button class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">Add Task</button>
                    <button id="edit_project_btn" class="btn btn-warning ml-2" data-value="false">Edit Project</button>
                    <button id="delete_project_btn" class="btn btn-danger ml-2">Delete Project</button>
                </@security.authorize>
            </div>
           <h3 class="text-center title mt-4">Tasks</h3>
           <div class="project_item_tasks">

           </div>
           <ul class="pagination d-flex justify-content-center"></ul>
       </div>
   </div>
</div>

<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form id="task_form" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Add Task</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="projectId" value="${id}">
                <input id="task_name" class="form-control mb-2" name="name" placeholder="Enter name of task" required>
                <input id="task_desc" class="form-control mb-3" name="description" placeholder="Enter description of task" required>
                <label>Priority</label>
                <select class="form-control" name="priority">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <div class="task_form_warnings my-3"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Add Task</button>
            </div>
        </form>
    </div>
</div>

<script>
    <#if userEmail??>
        getProjectItem(${id}, "${userEmail}");
        onAddTaskFormSubmit("${userEmail}")
        <#else>
        getProjectItem(${id})
        onAddTaskFormSubmit()
    </#if>

    $('#delete_project_btn').click(function (){
        onDeleteProjectBtnClick(${id})
    })
    $('#edit_project_btn').click(function (){
        onEditProjectBtnClick(${id})
    })

</script>
</body>
</html>




