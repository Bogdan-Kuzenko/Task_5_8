let host = 'http://localhost:8080';

$(document).ready(function () {

    const $modal = $('#create-subcategory-modal');
    const $subcategoryNameInput = $('#subcategory-name-input');
    const $subcategoryHide = $('#hidden');
    const $fileSelect = $('.file-select');
    const imagContainer = $('#uploaded-images');
    let categoryId;

    $modal.modal();

    /*
                ----------------Create---------------
    */
    $('#create-subcategory-btn').click(() => {

        getCategoriesToSelect();
    });

    $('#subcategory-create-button').click(() => {

        if ($subcategoryNameInput.val().length < 3) {
            alert('Min length 3 symbol');
            return;
        }
        const subcategoryRequest = {
            name: $subcategoryNameInput.val(),
            hideSubcategory: $subcategoryHide.is(':checked'),
            categoryId: $categorySelect.val()
        };

        let id = $subcategoryNameInput.attr('data-id');

        getBase64FromFile($fileSelect[0].files[0]).then(data => subcategoryRequest.image = data)  //if image exist -> set it
            .catch(() => subcategoryRequest.image = null) // if no image - set image to null
            .finally(() => { //send request after then or catch


                if (id) {
                    $.ajax({
                        url: `${host}/subcategory?id=${id}`,
                        type: 'put',
                        contentType: 'application/json',
                        data: JSON.stringify(subcategoryRequest),
                        success: () => {
                            getSubcategories();
                        },
                        error: logError

                    });


                } else {
                    $.ajax({
                        url: `${host}/subcategory`,
                        type: 'post',
                        contentType: 'application/json',
                        data: JSON.stringify(subcategoryRequest),
                        success: () => {
                            getSubcategories();
                        },
                        error: logError
                    });
                }

                $modal.modal('close');
                $subcategoryNameInput.val('');
                $subcategoryNameInput.attr('data-id', '');
                $subcategoryHide.prop('checked', false);
                $categorySelect.prop('selectedIndex', 0);
                $fileSelect.val("");

            });
    });

    /*
        -----------------delete button------------------
     */


    let actionOnDeleteButtons = () => {
        $('.delete-btn').click((e) => {
            let id = $(e.target).attr('data-id');
            $.ajax({
                url: `${host}/subcategory?id=${id}`,
                type: 'delete',
                success: () => {
                    console.log('delete');
                    $(e.target).closest('tr').hide();
                },
                error: logErrorAlert
            })
        })
    };


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
        img.setAttribute('src', 'http://localhost:8080/images/subcategory/' + fileName);
        document.getElementById('uploaded-images').appendChild(img);
        //  console.log( img.setAttribute('src', 'http://localhost:8080/images/subcategory/' + fileName))
    }

    /*
    -------------------Update button-----------
   */
    let actionOnUpdateButton = () => {
        $('.update-btn').click((e) => {
            let $btn = $(e.target);
            let id = $btn.attr('data-id');
            let image = $btn.attr('data-image');
            let $categoryName = $btn.parent().siblings('.category-name').text();
            categoryId = $btn.parent().siblings('.category-id').attr('data-id');


            getCategoriesToSelect();
            $(".category-select option:contains(" + $categoryName + ")").attr("selected", true);


            imagContainer.html('');

            addImgToContainer(image);


            $fileSelect.val("");

            let checkHidden = null;
            if ($btn.parent().siblings('.hidden-subcategory').html() === 'true') {
                checkHidden = true;
            } else {
                checkHidden = false;
            }

            $subcategoryNameInput.val($btn.parent().siblings('.subcategory-name').html());
            $subcategoryNameInput.attr('data-id', id);
            $subcategoryHide.prop('checked', checkHidden);

            $modal.modal('open');


        })
    };


    /*
           ----------------Get---------------
     */

    let tableBody = $('#subcategories');

    let appendSubcategoryToTable = (subcategory) => {
        tableBody.append(`
        <tr>
               <td>${subcategory.id}</td>
               <td class="hidden-subcategory">${subcategory.hideSubcategory}</td>
               <td class="category-name category-id" data-id="${subcategory.category.id}">${subcategory.category.name}</td>
               <td style="width: 150px;"class="image-container">
               <img src="http://localhost:8080/images/subcategory/${subcategory.image}">
               </td>
               <td class="subcategory-name"">${subcategory.name}</td>
               <td>
                    <button data-id="${subcategory.id}" class="delete-btn btn">Delete</button>
                    <button data-id="${subcategory.id}"  data-image="${subcategory.image}" class="update-btn btn">Update</button>

               </td>
         </tr>
            `);
    };


    let getSubcategories = () => {
        tableBody.html('');
        $.ajax({
            url: `${host}/subcategory`,
            type: 'get',
            success: (response) => {
                for (let subcat of response) {
                    appendSubcategoryToTable(subcat);
                }
                actionOnDeleteButtons();
                actionOnUpdateButton();
            }
        });
    };

    getSubcategories();


    /*
 ------------------------------ category--------------------------------
  */
    const $categorySelect = $('.category-select');

    const appendCategoryToSelect = (category) => {
        if (categoryId == category.id) {
            $categorySelect.append(`<option value="${category.id}" selected>${category.name}</option>`);
        } else {
            $categorySelect.append(`<option value="${category.id}" >${category.name}</option>`);
        }
    };


    const appendCategoriesToSelect = (categories) => {
        for (const category of categories) {
            appendCategoryToSelect(category)
        }
    };

    const activateSelect = (categories) => {
        $categorySelect.html('');
        if (categoryId === undefined) {
            $categorySelect.append(`<option class="category-name category-id" value="" disabled selected>Choose your category</option>`);
        }
        appendCategoriesToSelect(categories);
        $('select').formSelect();
    };

    let getCategoriesToSelect = () => {
        $categorySelect.html('');
        $.ajax({
            url: `${host}/category`,
            type: 'get',
            success: activateSelect,
            error: logError
        });
    };


    /*
     ----------------Other functions--------------
 */
    function logError(err) {
        console.log(err)
    }

    function logErrorAlert(err) {
        console.log(err);
        alert(err.responseText)

    }

});




