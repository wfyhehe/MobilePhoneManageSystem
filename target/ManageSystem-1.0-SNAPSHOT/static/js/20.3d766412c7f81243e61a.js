webpackJsonp([20],{229:function(e,t,a){function l(e){a(357)}var r=a(51)(a(280),a(392),l,"data-v-7efa01ec",null);e.exports=r.exports},249:function(e,t,a){e.exports={default:a(250),__esModule:!0}},250:function(e,t,a){var l=a(6),r=l.JSON||(l.JSON={stringify:JSON.stringify});e.exports=function(e){return r.stringify.apply(r,arguments)}},251:function(e,t,a){"use strict";function l(e,t){var a=void 0;return function(){for(var l=this,r=arguments.length,o=Array(r),n=0;n<r;n++)o[n]=arguments[n];a&&clearTimeout(a),a=setTimeout(function(){e.apply(l,o)},t)}}function r(e,t){t=t>0&&t<=20?t:2,e=parseFloat((e+"").replace(/[^\d.-]/g,"")).toFixed(t)+"";for(var a=e.split(".")[0].split("").reverse(),l=e.split(".")[1],r="",o=0;o<a.length;o++)r+=a[o]+((o+1)%3==0&&o+1!==a.length?",":"");return r.split("").reverse().join("")+"."+l}function o(){var e=(0,n.getToken)();if(!e)return!1;var t=s.TokenUtil.parseUuid(e);return"-"===t.charAt(t.length-1)}Object.defineProperty(t,"__esModule",{value:!0}),t.debounce=l,t.formatMoney=r,t.isSuperAdmin=o;var n=a(22),s=a(22)},280:function(e,t,a){"use strict";function l(e){return e&&e.__esModule?e:{default:e}}Object.defineProperty(t,"__esModule",{value:!0});var r=a(249),o=l(r),n=a(23),s=l(n),i=a(87),d=a(251);t.default={data:function(){return{searchForm:{name:"",type:""},addForm:{id:"",name:"",type:"",contact:"",tel:"",email:"",address:"",remark:""},suppliers:[],deletedSuppliers:[],types:[],addFormVisible:!1,loading:!0,loadingDeleted:!0,showDeleted:!1,pageIndex:1,pageSize:10,count:0,addRule:{id:[{validator:function(e,t,a){if(t)if(/^[0-9A-Z-]{1,15}$/.test(t)){var l=i.backEndUrl+"/supplier/check_supplier.do";s.default.get(l,{params:{id:t}}).then(function(e){e.data.status!==i.SUCCESS?a(new Error(e.data.msg)):a()})}else a(new Error("编号必须由1-15位数字、大写字母、减号组成"));else a(new Error("请输入编号"))},trigger:"blur"}],name:[{validator:function(e,t,a){t?a():a(new Error("请输入名称"))},trigger:"blur"}],email:[{validator:function(e,t,a){t?/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(t)?a():a(new Error("邮箱格式不正确")):a()},trigger:"blur"}]}}},computed:{searchFormJson:function(){return(0,o.default)(this.searchForm)}},watch:{searchFormJson:(0,d.debounce)(function(){this.getSuppliers()},500),$route:"getSuppliers"},methods:{getSuppliers:function(e){e%1!=0&&(e=null),this.loading=!0;var t=this,a=i.backEndUrl+"/supplier/get_suppliers.do";s.default.post(a,(0,o.default)({name:t.searchForm.name,type:t.searchForm.type,pageIndex:e||t.pageIndex,pageSize:10}),{headers:{"Content-Type":"application/json;charset=UTF-8"}}).then(function(e){e.data.status===i.SUCCESS?(t.suppliers=e.data.data,t.count=e.data.count,t.loading=!1):t.$message.error(e.data.msg)})},addSupplier:function(){this.addFormVisible=!0},onAddSubmit:function(){var e=this,t=i.backEndUrl+"/supplier/add_supplier.do";s.default.post(t,(0,o.default)({id:e.addForm.id,name:e.addForm.name,type:e.addForm.type,contact:e.addForm.contact,tel:e.addForm.tel,email:e.addForm.email,address:e.addForm.address,remark:e.addForm.remark}),{headers:{"Content-Type":"application/json;charset=UTF-8"}}).then(function(t){t.data.status===i.SUCCESS?(e.$message.success("添加成功"),e.getSuppliers(),e.addFormVisible=!1):e.$message.error(t.data.msg)})},deleteSupplier:function(e){var t=this,a=i.backEndUrl+"/supplier/delete_supplier.do";this.$confirm("此操作将删除供应商, 是否继续？（可操作数据库进行恢复）","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"danger"}).then(function(){s.default.get(a,{params:{id:e.id}}).then(function(e){e.data.status===i.SUCCESS?(t.getSuppliers(),t.$message({type:"success",message:"删除成功!"})):t.$message({type:"error",message:e.data.msg})})}).catch(function(){})},editSupplier:function(e){this.$router.push("/supplier/"+e.id)},recoverSupplier:function(e){var t=this,a=i.backEndUrl+"/supplier/recover_supplier.do";s.default.get(a,{params:{id:e.id}}).then(function(e){e.data.status===i.SUCCESS?(t.getSuppliers(),t.getDeletedSuppliers(),t.$message.success("恢复成功")):t.$message.error(e.data.msg)})},getDeletedSuppliers:function(){this.showDeleted=!0,this.loadingDeleted=!0;var e=this,t=i.backEndUrl+"/supplier/get_deleted_suppliers.do";s.default.get(t,{params:{}}).then(function(t){t.data.status===i.SUCCESS?(e.deletedSuppliers=t.data.data,e.loadingDeleted=!1):e.$message.error(t.data.msg)})},getTypes:function(){var e=this,t=i.backEndUrl+"/supplier_type/get_supplier_types.do";s.default.post(t,{}).then(function(t){t.data.status===i.SUCCESS?e.types=t.data.data:e.$message.error(t.data.msg)})},selectShowed:function(e){e&&this.getTypes()},hideRecover:function(){this.showDeleted=!1,this.loadingDeleted=!1}},mounted:function(){this.getSuppliers()}}},324:function(e,t,a){t=e.exports=a(214)(!0),t.push([e.i,".form2[data-v-7efa01ec]{margin:100px 0}.el-table .deleted-row[data-v-7efa01ec]{background-color:rgba(255,73,73,.56)}.recover[data-v-7efa01ec]{float:right;margin:10px 40px 10px 0}.add[data-v-7efa01ec]{float:left;margin:10px 40px 10px 10px}h1[data-v-7efa01ec],h2[data-v-7efa01ec],h3[data-v-7efa01ec]{margin:30px}","",{version:3,sources:["D:/Documents/webstormworkspace/ManageSystem/src/components/base_config/Supplier.vue"],names:[],mappings:"AAGA,wBACE,cAAgB,CACjB,AACD,wCACE,oCAA0C,CAC3C,AACD,0BACE,YAAa,AACb,uBAAyB,CAC1B,AACD,sBACE,WAAY,AACZ,0BAA4B,CAC7B,AACD,4DACE,WAAa,CACd",file:"Supplier.vue",sourcesContent:["\n.supplier-manage[data-v-7efa01ec] {\n}\n.form2[data-v-7efa01ec] {\n  margin: 100px 0;\n}\n.el-table .deleted-row[data-v-7efa01ec] {\n  background-color: rgba(255, 73, 73, 0.56);\n}\n.recover[data-v-7efa01ec] {\n  float: right;\n  margin: 10px 40px 10px 0;\n}\n.add[data-v-7efa01ec] {\n  float: left;\n  margin: 10px 40px 10px 10px;\n}\nh1[data-v-7efa01ec], h2[data-v-7efa01ec], h3[data-v-7efa01ec] {\n  margin: 30px;\n}\n"],sourceRoot:""}])},357:function(e,t,a){var l=a(324);"string"==typeof l&&(l=[[e.i,l,""]]),l.locals&&(e.exports=l.locals);a(215)("3851e729",l,!0)},392:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"supplier-manage"},[a("h2",[e._v("供应商管理")]),e._v(" "),a("el-button",{staticClass:"add",attrs:{size:"small"},on:{click:e.addSupplier}},[a("i",{staticClass:"el-icon-plus"}),e._v(" 添加供应商")]),e._v(" "),a("div",{staticClass:"search"},[a("el-form",{attrs:{inline:!0,model:e.searchForm}},[a("el-form-item",{attrs:{label:"供应商名"}},[a("el-input",{attrs:{placeholder:"供应商名"},model:{value:e.searchForm.name,callback:function(t){e.searchForm.name=t},expression:"searchForm.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"类别"}},[a("el-select",{attrs:{placeholder:"类别"},on:{"visible-change":e.selectShowed},model:{value:e.searchForm.type,callback:function(t){e.searchForm.type=t},expression:"searchForm.type"}},[a("el-option",{attrs:{label:"所有类别",value:""}}),e._v(" "),e._l(e.types,function(e,t){return a("el-option",{key:t,attrs:{label:e.name,value:e.name}})})],2)],1)],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.loading,expression:"loading",modifiers:{body:!0}}],staticStyle:{width:"100%"},attrs:{data:e.suppliers,align:"left","default-sort":{prop:"id",order:"ascending"}}},[a("el-table-column",{staticClass:"column",attrs:{prop:"id",sortable:"",label:"供应商编号"}}),e._v(" "),a("el-table-column",{staticClass:"column",attrs:{prop:"name",label:"供应商名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"type.name",sortable:"",label:"类别"}}),e._v(" "),a("el-table-column",{attrs:{prop:"contact",label:"联系人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"tel",label:"电话"}}),e._v(" "),a("el-table-column",{attrs:{prop:"email",label:"E-Mail"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),e._v(" "),a("el-table-column",{attrs:{prop:"remark",label:"备注"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{plain:!0,type:"info",icon:"edit",size:"small"},on:{click:function(a){e.editSupplier(t.row)}}}),e._v(" "),a("el-button",{attrs:{plain:!0,type:"danger",icon:"delete",size:"small"},on:{click:function(a){e.deleteSupplier(t.row)}}})]}}])})],1),e._v(" "),a("el-pagination",{staticClass:"pagination",attrs:{layout:"prev, pager, next",total:e.count,"current-page":e.pageIndex,"page-size":e.pageSize},on:{"current-change":e.getSuppliers}}),e._v(" "),a("div",{staticClass:"recover"},[a("el-button",{directives:[{name:"show",rawName:"v-show",value:!e.showDeleted,expression:"!showDeleted"}],attrs:{plain:!0,type:"info"},on:{click:e.getDeletedSuppliers}},[e._v("显示已删除供应商")]),e._v(" "),a("el-button",{directives:[{name:"show",rawName:"v-show",value:e.showDeleted,expression:"showDeleted"}],attrs:{plain:!0,type:"info"},on:{click:e.hideRecover}},[e._v("隐藏已删除供应商")])],1),e._v(" "),e.showDeleted?a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.loadingDeleted,expression:"loadingDeleted",modifiers:{body:!0}}],staticClass:"form2",staticStyle:{width:"100%"},attrs:{data:e.deletedSuppliers,"row-class-name":"deleted-row",align:"left","default-sort":{prop:"type.name",order:"descending"}}},[a("el-table-column",{staticClass:"column",attrs:{prop:"name",sortable:"",label:"供应商名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"type.name",sortable:"",label:"类别"}}),e._v(" "),a("el-table-column",{attrs:{prop:"contact",label:"联系人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"tel",label:"电话"}}),e._v(" "),a("el-table-column",{attrs:{prop:"email",label:"E-Mail"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),e._v(" "),a("el-table-column",{attrs:{prop:"remark",label:"备注"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{size:"small"},on:{click:function(a){e.recoverSupplier(t.row)}}},[e._v("恢复\n          ")])]}}])})],1):e._e()],1),e._v(" "),a("el-dialog",{attrs:{title:"新建供应商",visible:e.addFormVisible},on:{"update:visible":function(t){e.addFormVisible=t}}},[a("el-form",{attrs:{model:e.addForm,rules:e.addRule,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"供应商编号",prop:"id"}},[a("el-input",{model:{value:e.addForm.id,callback:function(t){e.addForm.id=t},expression:"addForm.id"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"名称",prop:"name"}},[a("el-input",{model:{value:e.addForm.name,callback:function(t){e.addForm.name=t},expression:"addForm.name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"类别",prop:"type"}},[a("el-select",{attrs:{placeholder:"请选择类别"},on:{"visible-change":e.selectShowed},model:{value:e.addForm.type,callback:function(t){e.addForm.type=t},expression:"addForm.type"}},[a("el-option",{attrs:{label:"未定",value:""}}),e._v(" "),e._l(e.types,function(e,t){return a("el-option",{key:t,attrs:{label:e.name,value:e.name}})})],2)],1),e._v(" "),a("el-form-item",{attrs:{label:"联系人",prop:"contact"}},[a("el-input",{model:{value:e.addForm.contact,callback:function(t){e.addForm.contact=t},expression:"addForm.contact"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"电话",prop:"tel"}},[a("el-input",{model:{value:e.addForm.tel,callback:function(t){e.addForm.tel=t},expression:"addForm.tel"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"E-Mail",prop:"email"}},[a("el-input",{model:{value:e.addForm.email,callback:function(t){e.addForm.email=t},expression:"addForm.email"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"地址",prop:"address"}},[a("el-input",{model:{value:e.addForm.address,callback:function(t){e.addForm.address=t},expression:"addForm.address"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备注",prop:"remark"}},[a("el-input",{attrs:{type:"textarea"},model:{value:e.addForm.remark,callback:function(t){e.addForm.remark=t},expression:"addForm.remark"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.addFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.onAddSubmit}},[e._v("确 定")])],1)],1),e._v(" "),a("router-view")],1)},staticRenderFns:[]}}});
//# sourceMappingURL=20.3d766412c7f81243e61a.js.map