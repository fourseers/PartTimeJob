import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import token from '@/util/token.js'
import {getShops} from '@/util/getShops.js'

describe('getShops.js', () => {
    beforeAll((done) => {

        const wrapper = shallowMount(Login);
        const vm = wrapper.vm
        vm.login_process("user_one", "user_one").then(response => {
            done();
        });
    });

    it('test getShops', done => {
        expect( getShops(0)).toEqual( );
        done();
    });

})
