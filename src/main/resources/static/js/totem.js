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
    var listR = '';

        for(i=0; i< Object.keys(dataB).length; i++){
            listR = listR + '<div class="accordion-item">\n';
            listR = listR + '  <h2 class="accordion-header" id="heading'+dataB[i].fileName.toString().trim()+'">\n';
            listR = listR + '    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse'+dataB[i].fileName.toString().trim()+'" aria-expanded="false" aria-controls="collapse'+dataB[i].fileName.toString().trim()+'">\n';
            listR = listR + '      '+dataB[i].fileName.toString().trim()+'\n';
            listR = listR + '    </button>\n';
            listR = listR + '  </h2>\n';
            listR = listR + '  <div id="collapse'+dataB[i].fileName.toString().trim()+'" class="accordion-collapse collapse" aria-labelledby="heading'+dataB[i].fileName.toString().trim()+'" data-bs-parent="#accordionExample">\n';
            listR = listR + '    <div class="accordion-body">\n';
            listR = listR + '      '+dataB[i].data.toString().trim()+'\n';
            listR = listR + '    </div>\n';
            listR = listR + '  </div>\n';
            listR = listR + '</div>\n';
        }

    document.getElementById("accordionExample").innerHTML = listR;
    document.getElementById('spinner').classList.add('visually-hidden');
}

