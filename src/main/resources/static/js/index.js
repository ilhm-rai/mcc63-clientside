$(document).ready(() => {
  getAllCountry();
  $('#countryForm').on('submit', (e) => {
    e.preventDefault();

    const country = {
      code: $('#code').val(),
      name: $('#name').val(),
      regionId: $('#regionId').val()
    };

    $.ajax({
      url: '/country/add',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(country),
      success: (res) => {
        toast().fire({
          icon: 'success',
          title: res.message
        });

        $('#countryModal').modal('hide');
        getAllCountry();
      },
      error: (err) => {
        console.log(err);
      }
    });
  });
  getAllRegion();
});

var getAllCountry = function () {
  $.ajax({
    url: '/country/get',
    type: 'GET',
    success: (res) => {
      let html = '';

      var i = 0;
      html = res.reduce((acc, curr) => {
        return acc + `
        <tr>
            <td>${++i}</td>
            <td>${curr.code}</td>
            <td>${curr.name}</td>
            <td>${curr.region.name}</td>
            <td>
                <button class="btn btn-sm btn-secondary me-2">Detail</button>
                <button class="btn btn-sm btn-secondary me-2">Update</button>
                <button type="submit" class="btn btn-sm btn-danger js-delete" data-id="${curr.id}">Delete</button>
            </td>
        </tr>
    `;
      }, html);

      $('#countryTable tbody').html(html);
      onDeleteCountry();
    },
    error: (err) => {
      console.log(err);
    }
  });
};

var getAllRegion = function () {
  $.ajax({
    url: '/region/get',
    type: 'GET',
    success: (res) => {
      let html = '';
      $.each(res, (i, region) => {
        html += `<option value="${region.id}">${region.name}</option>`;
      });

      $('#regionId').append(html);
    },
    error: (err) => {
      console.log(err);
    }
  });
};

var onDeleteCountry = function () {
  $('.js-delete').on('click', function () {
    var context = this;
    Swal.fire({
      title: 'Are you sure?',
      text: "data will be deleted permanently",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete'
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: '/country/remove/' + this.dataset.id,
          type: 'DELETE',
          success: (res) => {
            toast().fire({
              icon: 'success',
              title: res.message
            });
            getAllCountry();
          },
          error: (err) => {
            toast().fire({
              icon: 'error',
              title: err.message
            });
          }
        });
      }
    });
  });
};

function toast() {
  const Toast = swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', swal.stopTimer);
      toast.addEventListener('mouseleave', swal.resumeTimer);
    }
  });

  return Toast;
}