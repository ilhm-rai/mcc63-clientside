$(document).ready(() => {
  let id = null;
  var t = $('#countryTable').DataTable({
    "ajax": {
      "url": '/country/get',
      "dataSrc": ''
    },
    "columnDefs": [{
      "searchable": false,
      "orderable": false,
      "targets": 0
    }],
    "order": [[1, 'asc']],
    "columns": [
      { "data": 'id' },
      { "data": 'code' },
      { "data": 'name' },
      { "data": 'region.name' },
      {
        sortable: false,
        render: function (data, type, row) {
          return `<button class="btn btn-sm btn-primary me-2 js-update" data-country='${JSON.stringify(row)}'>Update</button>
                  <button type="submit" class="btn btn-sm btn-secondary js-delete" data-id="${row.id}">Delete</button>`;
        }
      }
    ]
  });

  t.on('order.dt search.dt', function () {
    t.column(0, { search: 'applied', order: 'applied' }).nodes().each(function (cell, i) {
      cell.innerHTML = i + 1;
    });
  }).draw();

  $(document).on("click", '.js-delete', function (event) {
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
          },
          error: (err) => {
            toast().fire({
              icon: 'error',
              title: err.message
            });
          }
        }).done(function (res) {
          $('#countryTable').DataTable().ajax.reload();
        });
      }
    });
  });

  $(document).on("click", '.js-create', function (event) {
    clearForm();
  });

  $(document).on("click", '.js-update', function (event) {
    let country = JSON.parse(this.dataset.country);
    id = country.id;
    $('#countryModal').modal('show');
    $('#code').val(country.code);
    $('#name').val(country.name);
    $('#regionId').val(country.region.id);
  });

  $('#countryForm').on('submit', (e) => {
    e.preventDefault();

    const country = {
      code: $('#code').val(),
      name: $('#name').val(),
      regionId: $('#regionId').val()
    };

    if (id) {
      country['id'] = id;
      $.ajax({
        url: '/country/update/' + id,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(country),
        success: (res) => {
          toast().fire({
            icon: 'success',
            title: res.message
          });

          $('#countryModal').modal('hide');
        },
        error: (err) => {
          console.log(err);
        }
      }).done(function (res) {
        clearForm();
        $('#countryTable').DataTable().ajax.reload();
      });
    } else {
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
        },
        error: (err) => {
          console.log(err);
        }
      }).done(function (res) {
        $('#countryTable').DataTable().ajax.reload();
      });
    };
  });

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
});

var clearForm = function () {
  id = null;
  $('#code').val("");
  $('#name').val("");
  $('#regionId').val("");
};

var toast = function () {
  const Toast = swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', swal.stopTimer);
      toast.addEventListener('mouseleave', swal.resumeTimer);
    }
  });

  return Toast;
};