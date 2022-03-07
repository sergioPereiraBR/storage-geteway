const urlBase = "https://gateway-storage.herokuapp.com";

function blobs(){ 
    var url = `${urlBase}/totemapi/v1/admin/all`;
    var xhttp = new XMLHttpRequest()

    document.getElementById('spinner').classList.remove('visually-hidden')

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var data = this.responseText; 
            
            resultado_blobs(JSON.parse(data));
            /*
            function resultado_blobs(data_resultado){
                (data)
                window.history.pushState(url);
            }
            */
        };
    }

    xhttp.open("GET", url, true);
    xhttp.send();
}

function resultado_blobs(dataB){
    var listR = "";
    var blobName = "";
    var totemData = "";
    var dataFormated = "";
    
    for(i=0; i< Object.keys(dataB).length; i++){
        blobName = dataB[i].fileName
        const objBlob = JSON.parse(blobName);
        totemData = dataB[i].data;
        dataFormated = formatData (totemData);

        listR = listR + '<div class="accordion-item">\n';
        listR = listR + '  <p class="accordion-header" id="heading'+objBlob+'" style="padding: 15px; overflow-wrap: break-word; word-wrap: break-word; background: rgb(231,241,255); color:#0d6efd">\n';
        listR = listR + '      <b>'+objBlob+'</b>\n';
        listR = listR + '  </p>\n';
        listR = listR + '  <div id="collapse'+objBlob+'" class="accordion-collapse" aria-labelledby="heading'+objBlob+'" data-bs-parent="#accordionExample">\n';
        listR = listR + '    <div class="accordion-body" style="overflow-wrap: break-word; word-wrap: break-word;">\n';
        listR = listR + '      '+dataFormated+'\n';
        listR = listR + '    </div>\n';
        listR = listR + '  </div>\n';
        listR = listR + '</div>\n';
    }

    document.getElementById("accordionExample").innerHTML = listR;
    document.getElementById('spinner').classList.add('visually-hidden');
}


function formatData (totemData) {
    const obj = JSON.parse(totemData);

    return "uploaded: <b>"+obj.uploaded+" </b>"+
    "<br>comportamento: <b>"+obj.comportamento+" </b>"+
    "<br>condicao: <b>"+obj.condicao+" </b>"+
    "<br>upload_manual: <b>"+obj.upload_manual+" </b>"+
    "<br>usina: <b>"+obj.usina+" </b>"+
    "<br>area: <b>"+obj.area+" </b>"+
    "<br>celula: <b>"+obj.celula+" </b>"+
    "<br>codigo: <b>"+obj.codigo+" </b>"+
    "<br>data_informada: <b>"+obj.data_informada+" </b>"+
    "<br>eh_sif: <b>"+obj.eh_sif+" </b>"+
    "<br>nivel: <b>"+obj.nivel+" </b>"+
    "<br>evento: <b>"+obj.evento.trim()+" </b>"+
    "<br>resolvido: <b>"+obj.resolvido+"</b>"+
    "<br>nome_relator: <b>"+obj.nome_relator.trim()+"</b>"+
    "<br>tema_evento: <b>"+obj.tema_evento+" </b>"+
    "<br>id: <b>"+obj.id+" </b>"+
    "<br>data_hora_incluso_relato: <b>"+obj.data_hora_incluso_relato+" </b>"+
    "<br>data_hora_upload: <b>"+obj.data_hora_upload+" </b><br><br>";
}
