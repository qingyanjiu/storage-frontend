<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userid">
      <el-input v-model="dataForm.userid" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="物品名称" prop="goodsname">
      <el-input v-model="dataForm.goodsname" placeholder="物品名称"></el-input>
    </el-form-item>
    <el-form-item label="所有者名字" prop="owername">
      <el-input v-model="dataForm.owername" placeholder="所有者名字"></el-input>
    </el-form-item>
    <el-form-item label="物品图片" prop="picture">
      <el-input v-model="dataForm.picture" placeholder="物品图片"></el-input>
    </el-form-item>
    <el-form-item label="物品收纳位置" prop="location">
      <el-input v-model="dataForm.location" placeholder="物品收纳位置"></el-input>
    </el-form-item>
    <el-form-item label="热点id" prop="hotpointid">
      <el-input v-model="dataForm.hotpointid" placeholder="热点id"></el-input>
    </el-form-item>
    <el-form-item label="物品类型" prop="typeid">
      <el-input v-model="dataForm.typeid" placeholder="物品类型"></el-input>
    </el-form-item>
    <el-form-item label="物品tag" prop="tag">
      <el-input v-model="dataForm.tag" placeholder="物品tag"></el-input>
    </el-form-item>
    <el-form-item label="购买时间" prop="buytime">
      <el-input v-model="dataForm.buytime" placeholder="购买时间"></el-input>
    </el-form-item>
    <el-form-item label="购买价格" prop="price">
      <el-input v-model="dataForm.price" placeholder="购买价格"></el-input>
    </el-form-item>
    <el-form-item label="丢弃时间" prop="throwtime">
      <el-input v-model="dataForm.throwtime" placeholder="丢弃时间"></el-input>
    </el-form-item>
    <el-form-item label="最近修改时间" prop="modifytime">
      <el-input v-model="dataForm.modifytime" placeholder="最近修改时间"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="goodscomment">
      <el-input v-model="dataForm.goodscomment" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="物品状态" prop="goodstatus">
      <el-input v-model="dataForm.goodstatus" placeholder="物品状态"></el-input>
    </el-form-item>
    <el-form-item label="建筑物id" prop="buildingid">
      <el-input v-model="dataForm.buildingid" placeholder="建筑物id"></el-input>
    </el-form-item>
    <el-form-item label="热点坐标" prop="xstart">
      <el-input v-model="dataForm.xstart" placeholder="热点坐标"></el-input>
    </el-form-item>
    <el-form-item label="热点坐标" prop="ystart">
      <el-input v-model="dataForm.ystart" placeholder="热点坐标"></el-input>
    </el-form-item>
    <el-form-item label="热点坐标" prop="xend">
      <el-input v-model="dataForm.xend" placeholder="热点坐标"></el-input>
    </el-form-item>
    <el-form-item label="热点坐标" prop="yend">
      <el-input v-model="dataForm.yend" placeholder="热点坐标"></el-input>
    </el-form-item>
    <el-form-item label="热点名称" prop="hotpointname">
      <el-input v-model="dataForm.hotpointname" placeholder="热点名称"></el-input>
    </el-form-item>
    <el-form-item label="热点描述" prop="hotpointcomment">
      <el-input v-model="dataForm.hotpointcomment" placeholder="热点描述"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="hotpointstatus">
      <el-input v-model="dataForm.hotpointstatus" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="建筑物名字" prop="buildingname">
      <el-input v-model="dataForm.buildingname" placeholder="建筑物名字"></el-input>
    </el-form-item>
    <el-form-item label="平面图url" prop="planpicture">
      <el-input v-model="dataForm.planpicture" placeholder="平面图url"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="buildingcomment">
      <el-input v-model="dataForm.buildingcomment" placeholder="描述"></el-input>
    </el-form-item>
    <el-form-item label="类型的显示名称" prop="typename">
      <el-input v-model="dataForm.typename" placeholder="类型的显示名称"></el-input>
    </el-form-item>
    <el-form-item label="父类型id,如果为空说明是父类型" prop="parenttypeid">
      <el-input v-model="dataForm.parenttypeid" placeholder="父类型id,如果为空说明是父类型"></el-input>
    </el-form-item>
    <el-form-item label="" prop="typecomment">
      <el-input v-model="dataForm.typecomment" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          goodsid: 0,
          userid: '',
          goodsname: '',
          owername: '',
          picture: '',
          location: '',
          hotpointid: '',
          typeid: '',
          tag: '',
          buytime: '',
          price: '',
          throwtime: '',
          modifytime: '',
          goodscomment: '',
          goodstatus: '',
          buildingid: '',
          xstart: '',
          ystart: '',
          xend: '',
          yend: '',
          hotpointname: '',
          hotpointcomment: '',
          hotpointstatus: '',
          buildingname: '',
          planpicture: '',
          buildingcomment: '',
          typename: '',
          parenttypeid: '',
          typecomment: ''
        },
        dataRule: {
          userid: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          goodsname: [
            { required: true, message: '物品名称不能为空', trigger: 'blur' }
          ],
          owername: [
            { required: true, message: '所有者名字不能为空', trigger: 'blur' }
          ],
          picture: [
            { required: true, message: '物品图片不能为空', trigger: 'blur' }
          ],
          location: [
            { required: true, message: '物品收纳位置不能为空', trigger: 'blur' }
          ],
          hotpointid: [
            { required: true, message: '热点id不能为空', trigger: 'blur' }
          ],
          typeid: [
            { required: true, message: '物品类型不能为空', trigger: 'blur' }
          ],
          tag: [
            { required: true, message: '物品tag不能为空', trigger: 'blur' }
          ],
          buytime: [
            { required: true, message: '购买时间不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '购买价格不能为空', trigger: 'blur' }
          ],
          throwtime: [
            { required: true, message: '丢弃时间不能为空', trigger: 'blur' }
          ],
          modifytime: [
            { required: true, message: '最近修改时间不能为空', trigger: 'blur' }
          ],
          goodscomment: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          goodstatus: [
            { required: true, message: '物品状态不能为空', trigger: 'blur' }
          ],
          buildingid: [
            { required: true, message: '建筑物id不能为空', trigger: 'blur' }
          ],
          xstart: [
            { required: true, message: '热点坐标不能为空', trigger: 'blur' }
          ],
          ystart: [
            { required: true, message: '热点坐标不能为空', trigger: 'blur' }
          ],
          xend: [
            { required: true, message: '热点坐标不能为空', trigger: 'blur' }
          ],
          yend: [
            { required: true, message: '热点坐标不能为空', trigger: 'blur' }
          ],
          hotpointname: [
            { required: true, message: '热点名称不能为空', trigger: 'blur' }
          ],
          hotpointcomment: [
            { required: true, message: '热点描述不能为空', trigger: 'blur' }
          ],
          hotpointstatus: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          buildingname: [
            { required: true, message: '建筑物名字不能为空', trigger: 'blur' }
          ],
          planpicture: [
            { required: true, message: '平面图url不能为空', trigger: 'blur' }
          ],
          buildingcomment: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
          typename: [
            { required: true, message: '类型的显示名称不能为空', trigger: 'blur' }
          ],
          parenttypeid: [
            { required: true, message: '父类型id,如果为空说明是父类型不能为空', trigger: 'blur' }
          ],
          typecomment: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.goodsid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.goodsid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storageviewgoods/info/${this.dataForm.goodsid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userid = data.storageViewGoods.userid
                this.dataForm.goodsname = data.storageViewGoods.goodsname
                this.dataForm.owername = data.storageViewGoods.owername
                this.dataForm.picture = data.storageViewGoods.picture
                this.dataForm.location = data.storageViewGoods.location
                this.dataForm.hotpointid = data.storageViewGoods.hotpointid
                this.dataForm.typeid = data.storageViewGoods.typeid
                this.dataForm.tag = data.storageViewGoods.tag
                this.dataForm.buytime = data.storageViewGoods.buytime
                this.dataForm.price = data.storageViewGoods.price
                this.dataForm.throwtime = data.storageViewGoods.throwtime
                this.dataForm.modifytime = data.storageViewGoods.modifytime
                this.dataForm.goodscomment = data.storageViewGoods.goodscomment
                this.dataForm.goodstatus = data.storageViewGoods.goodstatus
                this.dataForm.buildingid = data.storageViewGoods.buildingid
                this.dataForm.xstart = data.storageViewGoods.xstart
                this.dataForm.ystart = data.storageViewGoods.ystart
                this.dataForm.xend = data.storageViewGoods.xend
                this.dataForm.yend = data.storageViewGoods.yend
                this.dataForm.hotpointname = data.storageViewGoods.hotpointname
                this.dataForm.hotpointcomment = data.storageViewGoods.hotpointcomment
                this.dataForm.hotpointstatus = data.storageViewGoods.hotpointstatus
                this.dataForm.buildingname = data.storageViewGoods.buildingname
                this.dataForm.planpicture = data.storageViewGoods.planpicture
                this.dataForm.buildingcomment = data.storageViewGoods.buildingcomment
                this.dataForm.typename = data.storageViewGoods.typename
                this.dataForm.parenttypeid = data.storageViewGoods.parenttypeid
                this.dataForm.typecomment = data.storageViewGoods.typecomment
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storageviewgoods/${!this.dataForm.goodsid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'goodsid': this.dataForm.goodsid || undefined,
                'userid': this.dataForm.userid,
                'goodsname': this.dataForm.goodsname,
                'owername': this.dataForm.owername,
                'picture': this.dataForm.picture,
                'location': this.dataForm.location,
                'hotpointid': this.dataForm.hotpointid,
                'typeid': this.dataForm.typeid,
                'tag': this.dataForm.tag,
                'buytime': this.dataForm.buytime,
                'price': this.dataForm.price,
                'throwtime': this.dataForm.throwtime,
                'modifytime': this.dataForm.modifytime,
                'goodscomment': this.dataForm.goodscomment,
                'goodstatus': this.dataForm.goodstatus,
                'buildingid': this.dataForm.buildingid,
                'xstart': this.dataForm.xstart,
                'ystart': this.dataForm.ystart,
                'xend': this.dataForm.xend,
                'yend': this.dataForm.yend,
                'hotpointname': this.dataForm.hotpointname,
                'hotpointcomment': this.dataForm.hotpointcomment,
                'hotpointstatus': this.dataForm.hotpointstatus,
                'buildingname': this.dataForm.buildingname,
                'planpicture': this.dataForm.planpicture,
                'buildingcomment': this.dataForm.buildingcomment,
                'typename': this.dataForm.typename,
                'parenttypeid': this.dataForm.parenttypeid,
                'typecomment': this.dataForm.typecomment
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
