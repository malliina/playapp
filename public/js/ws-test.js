var webSocket;
$(function () {
    var sendMessage = function () {
        chatSocket.send(JSON.stringify(
            {text: $("#talk").val()}
        ))
        $("#talk").val('')
    }

    var receiveEvent = function (event) {
        var data = JSON.parse(event.data)

        // Handle errors
        if (data.error) {
//            chatSocket.close()
            $("#onError span").text(data.error)
            $("#onError").show()
            return
        } else {
            $("#onChat").show()
        }

        // Create the message element
        var el = $('<div class="message"><span></span><p></p></div>')
        $("span", el).text(data.user)
        $("p", el).text(data.message)
        $(el).addClass(data.kind)
        if (data.user == '@username') $(el).addClass('me')
        $('#messages').append(el)

        // Update the members list
        $("#members").html('')
        $(data.members).each(function () {
            $("#members").append('<li>' + this + '</li>')
        })
    }

    var handleReturnKey = function (e) {
        if (e.charCode == 13 || e.keyCode == 13) {
            e.preventDefault()
            sendMessage()
        }
    }

    $("#talk").keypress(handleReturnKey)
//    chatSocket.onmessage = receiveEvent
})
var onConnect = function () {
//    alert('connected')
}
var onMessage = function (event) {
//    var data = JSON.parse(event.data)
    var elem = $("<p>"+event.data+"</p>")
    $("#messages").append(elem)
}
var onClose = function () {
//    alert('closed')
}
var onError = function () {
//    alert('error')
}
var sendMessage = function () {
    webSocket.send(JSON.stringify({
        text: $("#in").val(),
        text2: $("#in").val()
    }))
}