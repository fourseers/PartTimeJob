import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import token from '@/util/token.js'

import {getIndustry} from '@/util/getIndustry.js'
import AddShop from "../../src/views/AddShop";

describe('getIndustry ', () => {

    it('tests getIndustry error ', async () => {
        const wrapper2 = shallowMount(AddShop)
        const vm2 = wrapper2.vm

        await    expect(vm2.get_industry()).rejects.toEqual(
            401);
    });
})

describe('getIndustry.js', () => {
    it('test getIndustry', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
          vm.login_process("Tim Cook", "some password").then(response => {
            // expect(token.loadToken().scope).toEqual(goodresponse.data.scope);
            getIndustry().then(res  => {
                expect(res.data[0].industry_id ).toEqual(1 );

                done();
            })
            done();
        });

    });
})

describe('getIndustry.js', () => {
    it('test getIndustry', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            const wrapper2 = shallowMount(AddShop)
            const vm2 = wrapper2.vm
            vm2.get_industry().then(res  => {
                expect(res.data[0].industry_id ).toEqual(1 );
                done();
            })
            done();
        });
    });
})
