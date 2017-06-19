function print() {
  	var data = 'Nome: ' + $('#nome').val() + "    Preço: " + $("#preco").slider().val();
  	window.java.print(data);
}

function changeButtonTitle(title) {
    document.getElementById("search").innerHTML = title;
}