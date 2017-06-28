function print() {
	var nome = $('#nome').val();
	var preco = $("#preco").slider().val();
	var bateria = $("#bateria").slider().val();
	var so = $('#so').selectpicker().val();
	var conectividade = $('#conectividade').selectpicker().val();
  	window.java.pesquisar(nome, preco, bateria, so, conectividade);
}

function changeButtonTitle(title) {
    document.getElementById("search").innerHTML = title;
}

function clearResults() {
	$('#tableBody').empty();	
}

function fillResults(smartphones) {
	var tbody = $('#tableBody');
	tbody.empty();
    for(var i = 0; i < smartphones.length; i++) {
    	var smartphone = smartphones[i];
    	var tr = '<tr/>';
        tr += ('<td>' + smartphone[0] + '</td>');
        tr += ('<td>' + smartphone[1] + '</td>');
        tr += ('<td>' + smartphone[2] + '</td>');
        tr += ('<td>' + smartphone[3] + '</td>');
        
        var conectividades = smartphone[4];
        var conect = "";
        for(var j = 0; j < conectividades.length; j++) {
        	conect = conect + (conectividades[j]);
        	if(i < conectividades.length - 1) {
        		conect = conect + ", ";
        	}
        }
        tr += ('<td>' + conect + '</td>');
        tr += ('</tr>');
        tbody.append(tr);
    }
}