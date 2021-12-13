const host = 'http://localhost:8080';


/*
--------------------------file----------------------------------
*/
const getBase64FromFile = (file) => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
};

function addImgToContainer(fileName) {
    let img = document.createElement('img');
    img.setAttribute('src', 'http://localhost:8080/images/category/' + fileName);
    document.getElementById('uploaded-images').appendChild(img);
    //  console.log( img.setAttribute('src', 'http://localhost:8080/images/category/' + fileName))
}


/*
            ----------------Create---------------
 */

$(document).ready(function () {
    let $modal = $('#create-category-modal');
    let $categoryNameInput = $('#category-name-input');
    let $categoryHide = $('#hidden');
    const $fileSelect = $('.file-select');
    const imagContainer = $('#uploaded-images');




    $modal.modal();

    $('#category-create-button').click(() => {
        if ($categoryNameInput.val().length < 3) {
            alert('Min length 3 symbol');
            return;
        }

        let categoryRequest = {
            name: $categoryNameInput.val(),
            hideCategory: $categoryHide.is(':checked'),
        };
            getBase64FromFile($fileSelect[0].files[0]).then(data => categoryRequest.image = data)  //if image exist -> set it
                .catch(() => categoryRequest.image = null) // if no image - set image to null
                .finally(() => { //send request after then or catch

        let id = $categoryNameInput.attr('data-id');

        if (id) {
            $.ajax({
                url: `${host}/category?id=${id}`,
                type: 'put',
                contentType: 'application/json',
                data: JSON.stringify(categoryRequest),
                success: () => {
                    getCategories();
                },
                error: logError

            });



        } else {
            $.ajax({
                url: `${host}/category`,
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(categoryRequest),
                success: () => {
                    getCategories();
                },
                error: logError
            });
        }

        $modal.modal('close');
        $categoryNameInput.val('');
        $categoryNameInput.attr('data-id', '');
        $categoryHide.prop('checked', false);

    });

    });


    /*
        -----------------delete button------------------
     */


    let actionOnDeleteButtons = () => {
        $('.delete-btn').click((e) => {
            let id = $(e.target).attr('data-id');
            $.ajax({
                url: `${host}/category?id=${id}`,
                type: 'delete',
                success: () => {
                    console.log('delete');
                    $(e.target).closest('tr').hide();
                }
            })
        })
    };




    /*
    -------------------Update button-----------
   */
    let actionOnUpdateButton = () => {
        $('.update-btn').click((e) => {
            let $btn = $(e.target);
            let id = $btn.attr('data-id');
            let image = $btn.attr('data-image');

            imagContainer.html('');
            addImgToContainer(image);


            let checkHidden = null;
            if($btn.parent().siblings('.hidden-category').html()=== 'true'){
                checkHidden = true;
            }else {
                checkHidden = false;
            }

            $categoryNameInput.val($btn.parent().siblings('.category-name').html());
            $categoryNameInput.attr('data-id', id);
            $categoryHide.prop('checked', checkHidden);

            $modal.modal('open');


        })
    };

    /*
           ----------------Get---------------
     */


    let tableBody = $('#categories');
    let appendCategoryToTable = (category) => {
        tableBody.append(`
        <tr>
               <td>${category.id}</td>
               <td class="hidden-category">${category.hideCategory}</td>
               <td style="width: 150px;">
               <img class="image-size" src="http://localhost:8080/images/category/${category.image}">
               </td>
               <td class="category-name">${category.name}</td>
               <td>
                    <button data-id="${category.id}" class="delete-btn btn">Delete</button>
                    <button data-id="${category.id}" data-image="${category.image}" class="update-btn btn">Update</button>

               </td>
         </tr>
            `);
    };


    let getCategories = () => {
        tableBody.html('');
        $.ajax({
            url: `${host}/category`,
            type: 'get',
            success: (response) => {
                for (let cat of response) {
                    appendCategoryToTable(cat);
                }
                actionOnDeleteButtons();
                actionOnUpdateButton();


            }
        });
    };

    getCategories();

});


/*
     ----------------Other functions--------------
 */
function logError(err) {
    console.log(err)
}
