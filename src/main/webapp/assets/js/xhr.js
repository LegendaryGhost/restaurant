/****************  Functions ****************/
const getXHR = () => {
    try {
        return new ActiveXObject('Msxml2.XMLHTTP');
    } catch (e) {
        try {
            return new ActiveXObject('Microsoft.XMLHTTP');
        } catch (e2) {
            try {
                return new XMLHttpRequest();
            } catch (e3) {
                return false;
            }
        }
    }
};

const sendXHRRequest = (url, method = 'GET', formData = null) => {
    return new Promise((resolve, reject) => {
        let xhr = getXHR();

        xhr.addEventListener('load', event => {
            resolve(event.target.responseText);
        });

        xhr.addEventListener('error', event => {
            reject(event.target.responseText);
        });

        xhr.open(method, url);

        let data = null;
        // Si il y a des données de formulaire à envoyer, nous devons définir l'en-tête de la requête sur 'application/x-www-form-urlencoded'
        if (formData) {
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            // Convertir FormData en une chaîne de requête
            data = new URLSearchParams();
            for (let pair of formData) {
                data.append(pair[0], pair[1]);
            }
        }

        xhr.send(data);
    });
};