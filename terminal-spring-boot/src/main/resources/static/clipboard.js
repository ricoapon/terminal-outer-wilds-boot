document.body.addEventListener('click', function() {
    const selectedText = getSelectionText();
    if (selectedText.trim().length === 0) {
        // We just clicked somewhere on the screen, so we select the input again.
        document.getElementById('command').focus()
        return
    }

    navigator.clipboard.writeText(selectedText);
});

function getSelectionText() {
    let text = "";
    if (window.getSelection) {
        text = window.getSelection().toString();
    } else if (document.selection && document.selection.type !== "Control") {
        text = document.selection.createRange().text;
    }
    return text;
}
