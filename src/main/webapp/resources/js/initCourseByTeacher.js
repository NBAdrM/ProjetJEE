window.onload = function (){
    fetch('../grade')
        .then(response => response.json())
        .then(courses => {
            let selectElement = document.getElementById('course');

            //Effacer les options actuelles
            selectElement.innerHTML ='';

            //Ajouter une option par défaut
            let defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Sélectionnez un cours';
            selectElement.appendChild(defaultOption);

            //Ajouter les cours daans la liste déroulante
            courses.forEach(course => {
                let option = document.createElement('option');
                option.value = course.id;
                option.textContent = course.name;
                selectElement.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des cours', error);
        });
}