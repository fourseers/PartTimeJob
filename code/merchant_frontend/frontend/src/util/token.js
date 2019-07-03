var token= {
    savetoken: function (token) {
        sessionStorage.setItem("access_token", token.access_token);
        //   sessionStorage.setItem("enabled",token.enabled);
        // sessionStorage.setItem("expires_in",token.expires_in);
        sessionStorage.setItem("refresh_token", token.refresh_token);
        sessionStorage.setItem("token_type", token.token_type);
        // sessionStorage.setItem("scope",token.token_type);
    },
    deleteToken: function () {
        sessionStorage.setItem("access_token", null);
        sessionStorage.setItem("enabled", null);
        sessionStorage.setItem("expires_in", null);
        sessionStorage.setItem("refresh_token", null);
        sessionStorage.setItem("token_type", null);
    },
    loadToken: function () {
        let tokenInfo = {};
        tokenInfo.access_token = sessionStorage.getItem("access_token");
        tokenInfo.enabled = sessionStorage.getItem("enabled");
        tokenInfo.expires_in = sessionStorage.getItem("expires_in");
        tokenInfo.refresh_token = sessionStorage.getItem("refresh_token");
        tokenInfo.token_type = sessionStorage.getItem("token_type");
        return tokenInfo;
    },
    getTokenFromService: function (vue, code, callback, error) {
        {
            var prefix = "https://da074679-0fbc-4e30-8c3a-e760e7f2c378.mock.pstmn.io"
            this.axios({
                headers: {
                    'Access-Control-Allow-Origin': "*",
                    'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                method: 'post',
                url: prefix + '/merchant/login',
                data: this.$qs.stringify({
                    username: it.user,
                    password: it.password
                })
            }).then(callback)
                .catch(error)
        }
    }
}

export default token;