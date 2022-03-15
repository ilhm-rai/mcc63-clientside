$(document).ready(() => {
  let id = null;
  var t = $('#employeeTable').DataTable({
    "ajax": {
      "url": '/employee/get',
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
      { "data": 'fullName' },
      { "data": 'email' },
      { "data": 'user.username' },
      {
        sortable: false,
        render: function (data, type, row) {
          return `<a href="/employee/detail/${row.id}" class="btn btn-sm btn-primary">Detail</a>
                  <button class="btn btn-sm btn-primary js-update" data-id='${row.id}'>Edit</button>
                  <button class="btn btn-sm btn-secondary js-delete" data-id="${row.id}">Delete</button>`;
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
          url: '/employee/remove/' + this.dataset.id,
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
          $('#employeeTable').DataTable().ajax.reload();
        });
      }
    });
  });

  $(document).on("click", '.js-create', function (event) {
    id = null;
    clearForm();
  });

  $(document).on("click", '.js-update', function (event) {
    id = this.dataset.id;

    $.ajax({
      url: '/employee/get/' + id,
      type: 'GET',
      success: (res) => {
        let employee = res;
        $('#employeeModal').modal('show');
        $('#fullName').val(employee.fullName);
        $('#email').val(employee.email);
        $('#address').val(employee.address);
        $('#username').val(employee.user.username);
        $('input.roleId[type="checkbox"]').prop('checked', false);
        $.each(employee.user.roles, (i, role) => {
          $(`input[id='${role.name}-${role.id}']`).prop('checked', true);
        });
      },
      error: (err) => {
        console.log(err);
      }
    });
  });

  $('#employeeForm').on('submit', (e) => {
    e.preventDefault();

    var roleIds = [];

    $.each($(".roleId:checked"), function () {
      roleIds.push($(this).val());
    });

    const employee = {
      fullName: $('#fullName').val(),
      email: $('#email').val(),
      address: $('#address').val(),
      user: {
        username: $('#username').val(),
        password: $('#password').val(),
      },
      roleIds: roleIds
    };

    if (id) {
      employee['id'] = id;
      $.ajax({
        url: '/employee/update/' + id,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(employee),
        success: (res) => {
          toast().fire({
            icon: 'success',
            title: res.message
          });

          $('#employeeModal').modal('hide');
        },
        error: (err) => {
          toast().fire({
            icon: 'error',
            title: err.message
          });
        }
      }).done(function (res) {
        clearForm();
        $('#employeeTable').DataTable().ajax.reload();
      });
    } else {
      $.ajax({
        url: '/employee/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(employee),
        success: (res) => {
          toast().fire({
            icon: 'success',
            title: res.message
          });

          $('#employeeModal').modal('hide');
        },
        error: (err) => {
          toast().fire({
            icon: 'error',
            title: err.message
          });
        }
      }).done(function (res) {
        $('#employeeTable').DataTable().ajax.reload();
      });
    };
  });

  $.ajax({
    url: '/role/get',
    type: 'GET',
    success: (res) => {
      let html = '';

      html = res.reduce((prev, curr) => {
        return prev + `
          <div class="form-check form-check-inline">
            <input class="form-check-input roleId" type="checkbox" name="roleIds[]" id="${curr.name}-${curr.id}" value="${curr.id}">
            <label class="form-check-label" for="${curr.name}-${curr.id}">${curr.name}</label>
          </div>
        `;
      }, html);

      $('#role').append(html);
    },
    error: (err) => {
      console.log(err);
    }
  });
});

var clearForm = function () {
  $('#fullName').val("");
  $('#email').val("");
  $('#address').val("");
  $('#username').val("");
  $('#password').val("");
  $('input.roleId[type="checkbox"]').prop('checked', false);
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