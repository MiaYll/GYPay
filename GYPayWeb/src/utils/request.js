import axios from "axios";
import store from "@/store";
 
const instance = axios.create({
    baseURL: 'http://server.guangyu7.cn/',
    timeout: 3000,
  });

function request(){
  if(store.state.account.username != null && instance.defaults.headers['name'] == undefined){
    instance.defaults.headers['name'] = store.state.account.username;
    instance.defaults.headers['password'] = store.state.account.password;
  }
  return instance
}


export default request