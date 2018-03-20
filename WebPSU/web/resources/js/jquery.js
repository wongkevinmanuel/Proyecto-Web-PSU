$(document).ready(function () {
    $('.menu li:has(ul)').click(function (e) {
        e.preventDefault();  /*No se redireccione al #*/


        /*Desplegar el menu*/
        if ($(this).hasClass('activado')) {
            /*Ocultar el submenu*/
            $(this).removeClass('activado');
            $(this).children('ul').slideUp();
        } else {
            $('.menu li ul').slideUp();
            $('.menu li').removeClass('activado');
            $(this).addClass('activado');
            $(this).children('ul').slideDown();
        }
    });

    /*Modo Movil*/
    $('.btn-menu').click(function () {
        $('.contenedor-menu .menu').slideToggle();
    });

    $(window).resize(function () {
        if ($(document).width() > 450) {
            $('.contenedor-menu .menu').css({'display': 'block'});
        }
        ;

        /*Al abrir menu y cierra y regresa que aparezca cerrado siempre*/
        if ($(document).width() < 450) {
            $('.contenedor-menu .menu').css({'display': 'none'});
            $('.menu li ul').slideUp();
            $('.menu li').removeClass('activado');
        }
        ;
    });

    /*A los subitems nos redirijan a la pagina deseada*/

    $('.menu li ul li a').click(function () {
        window.location.href = $(this).attr("href");

        /*$('.menu li ul li a').click(function(){
         window.open(this.href, $(this).attr('target'));});
         con ese código funciona las url y abre nueva ventana*/
    });
    /*Fin A los subitems nos redirijan a la pagina deseada*/
});


