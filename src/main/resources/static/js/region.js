$(document).ready(() => {
  let id = null;
  var t = $('#regionTable').DataTable({
    "ajax": {
      "url": '/region/get',
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
      { "data": 'name' },
      {
        sortable: false,
        render: function (data, type, row) {
          return `<button class="btn btn-sm btn-primary me-2 js-update" data-region='${JSON.stringify(row)}'>Update</button>
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
          url: '/region/remove/' + this.dataset.id,
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
          $('#regionTable').DataTable().ajax.reload();
        });
      }
    });
  });

  $(document).on("click", '.js-create', function (event) {
    clearForm();
  });

  $(document).on("click", '.js-update', function (event) {
    let region = JSON.parse(this.dataset.region);
    id = region.id;
    $('#regionModal').modal('show');
    $('#name').val(region.name);
  });

  $('#regionForm').on('submit', (e) => {
    e.preventDefault();

    const region = {
      name: $('#name').val(),
    };

    if (id) {
      region['id'] = id;
      $.ajax({
        url: '/region/update/' + id,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(region),
        success: (res) => {
          toast().fire({
            icon: 'success',
            title: res.message
          });

          $('#regionModal').modal('hide');
        },
        error: (err) => {
          console.log(err);
        }
      }).done(function (res) {
        clearForm();
        $('#regionTable').DataTable().ajax.reload();
      });
    } else {
      $.ajax({
        url: '/region/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(region),
        success: (res) => {
          toast().fire({
            icon: 'success',
            title: res.message
          });

          $('#regionModal').modal('hide');
        },
        error: (err) => {
          console.log(err);
        }
      }).done(function (res) {
        $('#regionTable').DataTable().ajax.reload();
      });
    };
  });

});

var clearForm = function () {
  id = null;
  $('#name').val("");
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