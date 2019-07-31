import {shallowMount,mount} from '@vue/test-utils'
import Register from '@/views/Register.vue'
import token from '@/util/token.js'

import axios from 'axios'
describe('Register.vue', () => {
    it('register() returns response', done => {

        const wrapper = shallowMount(Register);

        const vm = wrapper.vm
        vm.axios = axios;
        vm.register("user_two2w23", "user_two").then(response => {
            expect(response.data.scope).toEqual("merchant");
            done();
        });

    })

    it('register() returns error response', async () => {
        const wrapper = shallowMount(Register);

        const vm = wrapper.vm
        vm.axios = axios;

        await    expect( vm.register("user_two2w23", "user_two")).rejects.toEqual(
            400);
    });


})
