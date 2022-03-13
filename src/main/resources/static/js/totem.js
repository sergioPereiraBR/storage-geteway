const urlBase = "https://gateway-storage.herokuapp.com";

function getAllBlobs(){
    var xhttp = new XMLHttpRequest()
    var url = `${urlBase}/totemapi/v1/admin/all`;
    
    document.getElementById('spinner').classList.remove('visually-hidden')

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let data = this.responseText;        
            resultado_blobs(JSON.parse(data));
        };
    }

    xhttp.open("GET", url, true);
    xhttp.send();
}

function resultado_blobs(dataBlob){
    var htmlTags = "";
    var blobNameOfPackage = "";
    var blobPackage = "";
    var blobPackageFormated = "";
    
    for(i=0; i< Object.keys(dataBlob).length; i++){
        blobNameOfPackage = dataBlob[i].blobName
        const blobName = JSON.parse(blobNameOfPackage);
        blobPackage = dataBlob[i].data;
        blobPackageFormated = formatPackage (blobPackage);

        htmlTags = htmlTags + '<div class="accordion-item">\n';
        htmlTags = htmlTags + '  <p class="accordion-header" id="heading'+blobName+'" style="padding: 15px; overflow-wrap: break-word; word-wrap: break-word; background: rgb(231,241,255); color:#0d6efd">\n';
        htmlTags = htmlTags + '      <b>'+blobName+'</b>\n';
        htmlTags = htmlTags + '  </p>\n';
        htmlTags = htmlTags + '  <div id="collapse'+blobName+'" class="accordion-collapse" aria-labelledby="heading'+blobName+'" data-bs-parent="#accordionExample">\n';
        htmlTags = htmlTags + '    <div class="accordion-body" style="overflow-wrap: break-word; word-wrap: break-word;">\n';
        htmlTags = htmlTags + '      '+blobPackageFormated+'\n';
        htmlTags = htmlTags + '    </div>\n';
        htmlTags = htmlTags + '  </div>\n';
        htmlTags = htmlTags + '</div>\n';
    }

    document.getElementById("accordionExample").innerHTML = htmlTags;
    document.getElementById('spinner').classList.add('visually-hidden');
}

function formatPackage (blobPackage) {
    const package = JSON.parse(blobPackage);

    return "uploaded: <b>"+package.uploaded+" </b>"+
    "<br>comportamento: <b>"+package.comportamento+" </b>"+ //true/false
    "<br>condicao: <b>"+package.condicao+" </b>"+ //true/false
    "<br>upload_manual: <b>"+obpackagej.upload_manual+" </b>"+
    "<br>usina: <b>"+package.usina+" </b>"+ //unidade
    "<br>area: <b>"+package.area+" </b>"+
    "<br>celula: <b>"+package.celula+" </b>"+ //setor
    "<br>codigo: <b>"+package.codigo+" </b>"+
    "<br>data_informada: <b>"+package.data_informada+" </b>"+
    "<br>eh_sif: <b>"+package.eh_sif+" </b>"+ //true/false
    "<br>nivel: <b>"+package.nivel+" </b>"+ //true/false
    "<br>evento: <b>"+package.evento.trim()+" </b>"+
    "<br>resolvido: <b>"+package.resolvido+"</b>"+ // confirmar resposta [sim, pendente, não , ou, true/false]
    "<br>nome_relator: <b>"+package.nome_relator.trim()+"</b>"+
    "<br>tema_evento: <b>"+package.tema_evento+" </b>"+
    "<br>id: <b>"+opackagebj.id+" </b>"+
    "<br>data_hora_incluso_relato: <b>"+package.data_hora_incluso_relato+" </b>"+
    "<br>data_hora_upload: <b>"+package.data_hora_upload+" </b><br><br>";
}
