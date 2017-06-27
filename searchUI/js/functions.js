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