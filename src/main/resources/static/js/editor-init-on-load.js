(function () {
  async function initializeEditor() {
    if (!document.styleSheets.length && document.styleSheets.length === 0) {
      return;
    }

    ClassicEditor.create(
      document.querySelector('.simple-editor'),
      {
        toolbar: ['bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote'],
      },
    );

    ClassicEditor.create(document.querySelector('.rich-editor'));
  }

  window.addEventListener("load", initializeEditor);
})();