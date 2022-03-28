//const urlBase = "https://gateway-storage.herokuapp.com";
const urlBase = "https://totem-teste.azurewebsites.net";

function getAllBlobs(){
    var xhttp = new XMLHttpRequest()
    var url = `${urlBase}/totemapi/v1/admin/all`;
    
    document.getElementById('spinner').classList.remove('visually-hidden')

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let packagesJSON = this.responseText;        
            viewPackages(JSON.parse(packagesJSON));
        }
    }

    xhttp.onerror = function () {
        console.log("An error occurred during the access.");
        document.getElementById('spinner').classList.add('visually-hidden');
    };

    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
    xhttp.timeout = 4000;
    xhttp.ontimeout = function () { 
        document.getElementById('spinner').classList.add('visually-hidden');
    }
    xhttp.send();
}

function viewPackages(packagesJSON) {
    var htmlTags = "";
    var packageNameJSON = "";
    var packageJSON = "";
    var packageFormated = "";
    var packageName = "";

    console.log(packagesJSON);

    for(i=0; i< Object.keys(packagesJSON).length; i++){
        packageNameJSON = packagesJSON[i].fileName;
        packageJSON = packagesJSON[i].data;
        packageName = JSON.parse(packageNameJSON);
        packageFormated = formatHtmlPackage (packageJSON);

        htmlTags = htmlTags + '<div class="accordion-item">\n';
        htmlTags = htmlTags + '  <p class="accordion-header"id="heading'+packageName+'" style="padding: 15px; overflow-wrap: break-word; word-wrap: break-word; background: rgb(231,241,255); color:#0d6efd">\n';
        htmlTags = htmlTags + '      <b>'+packageName+'</b>\n';
        htmlTags = htmlTags + '  </p>\n';
        htmlTags = htmlTags + '  <div id="collapse'+packageName+'" class="accordion-collapse" aria-labelledby="heading'+packageName+'" data-bs-parent="#accordionExample">\n';
        htmlTags = htmlTags + '    <div class="accordion-body" style="overflow-wrap: break-word; word-wrap: break-word;">\n';
        htmlTags = htmlTags + '      '+packageFormated+'\n';
        htmlTags = htmlTags + '    </div>\n';
        htmlTags = htmlTags + '  </div>\n';
        htmlTags = htmlTags + '</div>\n';
    }

    document.getElementById("accordionExample").innerHTML = htmlTags;
    document.getElementById('spinner').classList.add('visually-hidden');
}

function formatHtmlPackage (packageJSON) {
    const package = JSON.parse(packageJSON);

    return "uploaded: <b>"+package.uploaded+" </b>"+
    "<br>comportamento: <b>"+package.comportamento+" </b>"+
    "<br>condicao: <b>"+package.condicao+" </b>"+
    "<br>upload_manual: <b>"+package.upload_manual+" </b>"+
    "<br>usina: <b>"+package.usina+" </b>"+
    "<br>area: <b>"+package.area+" </b>"+
    "<br>celula: <b>"+package.celula+" </b>"+
    "<br>codigo: <b>"+package.codigo+" </b>"+
    "<br>data_informada: <b>"+package.data_informada+" </b>"+
    "<br>eh_sif: <b>"+package.eh_sif+" </b>"+
    "<br>nivel: <b>"+package.nivel+" </b>"+
    "<br>evento: <b>"+package.evento.trim()+" </b>"+
    "<br>resolvido: <b>"+package.resolvido+"</b>"+
    "<br>nome_relator: <b>"+package.nome_relator.trim()+"</b>"+
    "<br>tema_evento: <b>"+package.tema_evento+" </b>"+
    "<br>id: <b>"+package.id+" </b>"+
    "<br>data_hora_incluso_relato: <b>"+package.data_hora_incluso_relato+" </b>"+
    "<br>data_hora_upload: <b>"+package.data_hora_upload+" </b><br><br>";
}
