import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
import token from '@/util/token.js'
import {getShops} from '@/util/getShops.js'
import ManageShop from "../../src/views/ManageShop";

describe('getShops.js', () => {
    it('tests getshop error ', async () => {
        const wrapper2 = shallowMount(ManageShop)
        const vm2 = wrapper2.vm

        await    expect(vm2.mockTableData1(0)).rejects.toEqual(
            401);
    });

})

describe('getShops.js', () => {

    it('test getShops', done => {
         const wrapper = shallowMount(Login)
        const vm = wrapper.vm
          vm.login_process("Tim Cook", "some password").then(response => {
           // expect(token.loadToken().scope).toEqual(goodresponse.data.scope);
            getShops(0).then(res  => {
                expect(res.data.content[0].shop_id).toEqual(1);
                done();
            })
            done();
        });

    });
})

describe('getShops.js', () => {
    it('test getShops', done => {
        const wrapper = shallowMount(Login)
        const vm = wrapper.vm
        vm.login_process("Tim Cook", "some password").then(response => {
            const wrapper2 = shallowMount(ManageShop)
            const vm2 = wrapper2.vm
              vm2.mockTableData1(0).then(res  => {
                expect(res.data.content[0].shop_id).toEqual(1);
                done();
            })
            done();
        });
    });
})


