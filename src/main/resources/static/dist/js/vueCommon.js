const vueFilters = {
    tel: function (v) {
        let vv = "";
        if (v == null) {
            vv = "";
        } else if (v.length == 8) {
            vv = v.replace(/(\d{4})(\d{4})/, '$1-$2');
        } else if (v.length == 9) {
            vv = v.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
        } else if (v.length == 10) {
            if (v.indexOf('02') == 0) {
                vv = v.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
            } else {
                vv = v.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            }
        } else if (v.length == 11) {
            if (v.indexOf('050') == 0) {
                vv = v.replace(/(\d{4})(\d{3})(\d{4})/, '$1-$2-$3');
            } else {
                vv = v.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        } else if (v.length == 12) {
            vv = v.replace(/(\d{4})(\d{4})(\d{4})/, '$1-$2-$3');
        } else {
            vv = v;
        }
        return vv;
    }
};

const vueMethods = {
    getUserInfo : function () {
        let vm = this;
        axios.get('/account/getUserInfo')
            .then(res => {
                vm.user = res.data;
                console.log(vm.user);
                if(vm.user.length < 1) {
                    vm.isUser = false;
                }
                else{
                    localStorage.setItem('userInfo', JSON.stringify(vm.user));
                    Kakao.Auth.setAccessToken(vm.user.token);
                    vm.isUser = true;
                }
            })
            .catch(error => {
              console.error("Error fetching data: ", error);
            })
    }
}
