let currentRating = 0;

        function rateProfessional(rating) {
            currentRating = rating;
            updateStars();
        }

        function updateStars() {
            const stars = document.querySelectorAll('.fa-star');

            stars.forEach((star, index) => {
                if (index < currentRating) {
                    star.classList.add('checked');
                } else {
                    star.classList.remove('checked');
                }
            });

            document.getElementById('rating').textContent = currentRating;
        }