const modal = new mdb.Modal(exampleModal);

function saveData(e)
{   
    e.preventDefault();

    const formData = new FormData(form);
    const event = {
        title: formData.get("title"),
        dateStart: formData.get("dateStart"),
        dateEnd: formData.get("dateEnd"),
        idWorkspace: formData.get("idWorkspace")
    };

    fetch('/', {
        method: 'POST',
        headers: {
            'Content-Type': 'json'
        },
        body: JSON.stringify(event)
    })
    .then(response => response.json())
    .then(data => {
        if(data.error && data.error.message)
        { Swal.fire('Error', data.error.message, 'error'); }
        else
        {
            Swal.fire('Succes', 'You are being redirected', 'success');
            setTimeout(() => window.location.reload(), 2000);
        }
    });
}

form.onsubmit = saveData;
