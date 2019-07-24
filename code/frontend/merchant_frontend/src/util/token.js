var token= {
    savetoken: function (token) {
        sessionStorage.setItem("access_token", token.access_token);
        sessionStorage.setItem("expires_in",token.expires_in);
        sessionStorage.setItem("refresh_token", token.refresh_token);
        sessionStorage.setItem("token_type", token.token_type);
        sessionStorage.setItem("scope",token.scope);
    },
    deleteToken: function () {
        sessionStorage.clear()
    },
    loadToken: function () {
        let tokenInfo = {};
        tokenInfo.access_token = sessionStorage.getItem("access_token");
        tokenInfo.expires_in = sessionStorage.getItem("expires_in");
        tokenInfo.refresh_token = sessionStorage.getItem("refresh_token");
        tokenInfo.token_type = sessionStorage.getItem("token_type");
        tokenInfo.scope = sessionStorage.getItem("scope");
        return tokenInfo;
    },
}

export default token;
