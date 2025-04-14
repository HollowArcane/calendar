function initWatch()
{
    const ws = new WebSocket('ws://127.0.0.1:1080/check');

    ws.onmessage = message => {
        const json = JSON.parse(message.data);
        if(json.action === "reload")
        { window.location.reload(); }
        else
        { ws.send('Hello, Server'); }
    };
    ws.onclose = initWatch;
}
initWatch();
