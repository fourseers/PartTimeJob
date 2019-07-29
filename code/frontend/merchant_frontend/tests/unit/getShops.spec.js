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
        const shopres={"address": "ew", "brand": "e", "city": "110100", "industry": 1, "introduction": "we", "latitude": 0.2, "longitude": 0.2, "province": "110000", "shop_id": 11, "shop_name": "w"}
        vm.login_process("user_one", "user_one").then(response => {
           // expect(token.loadToken().scope).toEqual(goodresponse.data.scope);
            getShops(0).then(res  => {
                expect(res.data.content[0].longitude).toEqual(shopres.longitude);
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
        vm.login_process("user_one", "user_one").then(response => {
            const wrapper2 = shallowMount(ManageShop)
            const vm2 = wrapper2.vm
            const shopres={"address": "ew", "brand": "e", "city": "110100", "industry": 1, "introduction": "we", "latitude": 0.2, "longitude": 0.2, "province": "110000", "shop_id": 11, "shop_name": "w"}
            vm2.mockTableData1(0).then(res  => {
                expect(res.data.content[0].longitude).toEqual(shopres.longitude);
                done();
            })
            done();
        });
    });
})


