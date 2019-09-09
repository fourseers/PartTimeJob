import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import InfoConfig from '@/views/InfoConfig.vue'
import token from '@/util/token.js'
import Vue from "vue";

describe('Info  ', () => {
    beforeAll((done) => {

        const wrapper = shallowMount(Login);
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            done();
        });
    });

    it('test get company', done => {
        const wrapper = shallowMount(InfoConfig, {

            propsData: {
                logged:true
            }
        });
        const vm = wrapper.vm


        vm.getcompany().then(response => {
            expect(response ).toEqual(g );
            done();
        });
    });

})
