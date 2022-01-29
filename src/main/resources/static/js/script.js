function getFormatedDate(createdDate) {
    return createdDate.substring(0, 10) + '---' + createdDate.substring(11, 19);
}

function handleTopics(response, callback) {
    $('#topics').empty()
    $('.pagination').empty()
    response.content.map(topic => {
        let createdDate = getFormatedDate(topic.createdAt)
        let topic_elem = $(`
            <a href="/topics/${topic.id}" class="topic_item">
                <h3>${topic.name}</h3>
                <h4>${createdDate}</h4>
                <p>${topic.user.fullName}</p>
            </a>
        `)
        $('#topics').append(topic_elem)
    })

    handlePagination(response, callback)
}

function getAllTopics(pageNumber = 0) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/topics?page=' + pageNumber,
        success: (response) => {
            handleTopics(response, (i) => getAllTopics(i))
        }
    })
}

function handleTopicItem(response) {
    $('.topic_item_header h3').text(response.name)
    let createdDate = getFormatedDate(response.createdAt)
    $('.topic_item_header p').text(createdDate)
    $('.topic_item_header span').text(response.user.fullName)
    $('.topic_item_desc_inner p').text(response.description)
}

function handlePagination(response, callback) {
    if(!response.empty) {
        for(let i = 0; i < response.totalPages; i++) {
            let pagination_item = $(`<li class="page-item"><a class="page-link" href="#">${i + 1}</a></li>`)
            pagination_item.click(function (e){
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

function handleAnswers(response, callback) {
    $('.topic_item_answers').empty()
    $('.pagination').empty()
    response.content.map(answer => {
        let createdDate = getFormatedDate(answer.createdAt)
        let answer_item = $(`
               <div class="topic_item_answer my-4">
                    <div class="topic_item_answer_header mb-2 d-flex align-items-center">
                        <h4>${answer.user.fullName}</h4>
                        <span>${createdDate}</span>
                    </div>
                    <p>${answer.message}</p>
                </div>
        `)

        $('.topic_item_answers').append(answer_item)
    })

    handlePagination(response, (pageNumber) => callback(response.content[0].topic.id, pageNumber))

}

function getAnswersByTopicId(topicId, pageNumber = 0) {
    $.ajax({
        method: "GET",
        url: 'http://localhost:8080/answers/' + topicId + '?page=' + pageNumber,
        success: function (data) {
           handleAnswers(data, (topicId, pageNumber) => getAnswersByTopicId(topicId, pageNumber))
        }
    });
}

function getTopicItem(id) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/topics/get/' + id,
        success: (response) => {
            handleTopicItem(response)
            getAnswersByTopicId(response.id)
        },
        error: (error) => {
            $('.topic').empty()
            $('.topic_not_found').append($(`<h3 class="text-center">${error.responseText}</h3>`))
        }
    })
}

function onAddAnswerFormSubmit() {
    $('#answer_form').submit(function (e) {
        e.preventDefault()
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');
        const formData = new FormData(document.getElementById('answer_form'))

        let form = {
            topicId: formData.get("topicId"),
            message: formData.get("message")
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/answers',
            data: form,
            success: () => {
                $('#exampleModalCenter').modal('hide')
                $('#answer_message_input').val("")
                $('.answer_form_warnings').empty()
                getAnswersByTopicId(formData.get("topicId"))
            },
            error: (response) => {
                $('.answer_form_warnings').empty()
                response['responseJSON'].map(message => {
                    let p = $(`<p class="warning_message">${message}</p>`)
                    $('.answer_form_warnings').append(p)
                })
            }
        })
    })
}

function onAddTopicFormSubmit() {
    $('#add_topic_form').submit(function (e) {
        e.preventDefault()
        var _csrf_header = $('meta[name=_csrf_header]').attr('content');
        var _csrf_token = $('meta[name=_csrf_token]').attr('content');
        const formData = new FormData(document.getElementById('add_topic_form'))

        let form = {
            name: formData.get("name"),
            description: formData.get("description")
        }

        $.ajax({
            method: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf_token);
            },
            url: 'http://localhost:8080/topics',
            data: form,
            success: () => {
                window.location.replace("http://localhost:8080");
            },
            error: (response) => {
                $('.add_topic_warnings').empty()
                response['responseJSON'].map(message => {
                    let p = $(`<p class="warning_message">${message}</p>`)
                    $('.add_topic_warnings').append(p)
                })
            }
        })
    })
}