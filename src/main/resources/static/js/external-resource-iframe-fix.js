(function () {
  function getDocHeight(doc) {
    doc = doc || document;
    const body = doc.body, html = doc.documentElement;
    return Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
  }

  function setIframeHeight(id) {
    const iframe = document.getElementById(id);
    const doc = iframe.contentDocument ? iframe.contentDocument : iframe.contentWindow.document;
    iframe.style.visibility = 'hidden';
    iframe.style.height = "10px";
    iframe.style.height = getDocHeight(doc) + 4 + "px";
    iframe.style.visibility = 'visible';
  }

  let iframes = document.getElementsByClassName("external-resource");

  for (const iframe of iframes) {
    iframe.onload = function () {
      setIframeHeight(this);
      console.log("iframe on load!");
    }
  }
})();