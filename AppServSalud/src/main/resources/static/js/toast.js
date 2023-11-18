const toastExito = document.getElementById('success-toast-div');
const btn = document.getElementById('exito-toast');
const toastError = document.getElementById('error-toast-div');
const btnError = document.getElementById('error-toast');

if (toastExito !== null) {
    btn.addEventListener('click', function () {
        toastExito.style.right = '-100%';
    });
}

if (toastError !== null) {
    btnError.addEventListener('click', function () {
        toastError.style.right = '-100%';
    });
}

const decrementar = () => {
    const progressBar = document.getElementById('progress-bar-success');
    if (progressBar === null) {
        return;
    }
    const valorActual = progressBar.style.width;
    const valorActualNum = parseInt(valorActual);
    if (valorActualNum > 0) {
        progressBar.style.width = (valorActualNum - 1.5) + '%';
    } else {
        setTimeout(function () {
            toastExito.style.right = '-100%';
        }, 1000);
    }
};

const decrementar2 = () => {
    const progressBar = document.getElementById('progress-bar-error');
    if (progressBar === null) {
        return;
    }
    const valorActual = progressBar.style.width;
    const valorActualNum = parseInt(valorActual);
    if (valorActualNum > 0) {
        progressBar.style.width = (valorActualNum - 1.5) + '%';
    } else {
        setTimeout(function () {
            toastError.style.right = '-100%';
        }, 1000);
    }
};

window.onload = function () {
    if (toastExito != null) {
        toastExito.style.right = '0';
    }
    if (toastError != null) {
        toastError.style.right = '0';
    }
    setInterval(function () {
        decrementar();
    }, 80);
    setInterval(function () {
        decrementar2();
    }, 80);
    setTimeout(() => {
        toastExito.style.display = 'none';
    }, 7000);

}