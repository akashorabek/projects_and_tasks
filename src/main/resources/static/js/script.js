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
                <span>Ответов: ${topic.answers.length}</span>
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

function handleRateAnswer(liked, answerId, pageNumber, callback) {
    var _csrf_header = $('meta[name=_csrf_header]').attr('content');
    var _csrf_token = $('meta[name=_csrf_token]').attr('content');
    $.ajax({
        method: 'POST',
        url: 'http://localhost:8080/answers/rates/' + liked,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(_csrf_header, _csrf_token);
        },
        data: {
            answerId: answerId
        },
        success: () => {
            callback(pageNumber)
        }
    })
}

function handleAnswers(response, callback, topicId, userEmail) {
    $('.topic_item_answers').empty()
    $('.pagination').empty()
    response.content.map(answer => {
        let createdDate = getFormatedDate(answer.createdAt)
        let hasLiked;
        let hasDisliked;
        let likesCount = 0;
        let dislikesCount = 0;
        if (userEmail != null) {
            answer.answerRates.map(r => {
                if (r.user.email == userEmail && r.liked == true) {
                    hasLiked = true;
                }
                if (r.user.email == userEmail && r.disliked == true) {
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
            handleRateAnswer("liked", answer.id, response.number, (pageNumber) => callback(topicId, pageNumber, userEmail))
        })
        dislike.click(() => {
            handleRateAnswer("disliked", answer.id, response.number, (pageNumber) => callback(topicId, pageNumber, userEmail))
        })
        if (hasLiked) {
            like.find('i').css('color', 'red')
        }
        if (hasDisliked) {
            dislike.find('i').css('color', 'red')
        }
        let answer_item = $(`
               <div class="topic_item_answer my-4">
                    <div class="topic_item_answer_header mb-2 d-flex align-items-center">
                        <h4>${answer.user.fullName}</h4>
                        <span>${createdDate}</span>
                    </div>
                    <p>${answer.message}</p>
                    <div class="topic_item_answer_rates d-flex"></div>
                </div>
        `)

        answer_item.find('.topic_item_answer_rates').append(like, dislike);
        $('.topic_item_answers').append(answer_item)
    })

    handlePagination(response, (pageNumber) => callback(topicId, pageNumber, userEmail))

}

function getAnswersByTopicId(topicId, pageNumber = 0, userEmail = null) {
    $.ajax({
        method: "GET",
        url: 'http://localhost:8080/answers/' + topicId + '?page=' + pageNumber,
        success: function (data) {
           handleAnswers(data, (topicId, pageNumber) => getAnswersByTopicId(topicId, pageNumber, userEmail), topicId, userEmail)
        }
    });
}

function getTopicItem(id, userEmail = null) {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/topics/get/' + id,
        success: (response) => {
            handleTopicItem(response)
            getAnswersByTopicId(response.id, 0, userEmail)
        },
        error: (error) => {
            $('.topic').empty()
            $('.topic_not_found').append($(`<h3 class="text-center">${error.responseText}</h3>`))
        }
    })
}

function onAddAnswerFormSubmit(userEmail = null) {
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
                getAnswersByTopicId(formData.get("topicId"), 0, userEmail)
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