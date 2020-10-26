<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <!-- <el-form-item label="用户id" prop="userid">
      <el-input v-model="dataForm.userid" placeholder="用户id"></el-input>
    </el-form-item> -->
    <el-form-item label="物品名称" prop="goodname">
      <el-input v-model="dataForm.goodname" placeholder="物品名称"></el-input>
    </el-form-item>
    <el-form-item label="所有者名字" prop="owername">
      <el-input v-model="dataForm.owername" placeholder="所有者名字"></el-input>
    </el-form-item>
    <el-form-item label="物品图片" prop="picture">
      <el-input v-model="dataForm.picture" placeholder="物品图片"></el-input>
    </el-form-item>
    <el-form-item label="热点id" prop="hotpointid">
      <el-input v-model="dataForm.hotpointid" placeholder="热点id"></el-input>
    </el-form-item>
    <el-form-item label="物品收纳位置" prop="location">
      <el-input v-model="dataForm.location" placeholder="物品收纳位置"></el-input>
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
    <el-form-item label="备注" prop="comment">
      <el-input v-model="dataForm.comment" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="物品状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="物品状态"></el-input>
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
          // userid: '',
          goodname: '',
          owername: '',
          picture: '',
          hotpointid: '',
          location: '',
          typeid: '',
          tag: '',
          buytime: '',
          price: '',
          throwtime: '',
          modifytime: '',
          comment: '',
          status: ''
        },
        dataRule: {
          // userid: [
          //   { required: true, message: '用户id不能为空', trigger: 'blur' }
          // ],
          goodname: [
            { required: true, message: '物品名称不能为空', trigger: 'blur' }
          ],
          owername: [
            { required: true, message: '所有者名字不能为空', trigger: 'blur' }
          ],
          picture: [
            { required: true, message: '物品图片不能为空', trigger: 'blur' }
          ],
          hotpointid: [
            { required: true, message: '热点id不能为空', trigger: 'blur' }
          ],
          location: [
            { required: true, message: '物品收纳位置不能为空', trigger: 'blur' }
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
          comment: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '物品状态不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/storage/storagegoods/info/${this.dataForm.goodsid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                // this.dataForm.userid = data.storageGoods.userid
                this.dataForm.goodname = data.storageGoods.goodname
                this.dataForm.owername = data.storageGoods.owername
                this.dataForm.picture = data.storageGoods.picture
                this.dataForm.hotpointid = data.storageGoods.hotpointid
                this.dataForm.location = data.storageGoods.location
                this.dataForm.typeid = data.storageGoods.typeid
                this.dataForm.tag = data.storageGoods.tag
                this.dataForm.buytime = data.storageGoods.buytime
                this.dataForm.price = data.storageGoods.price
                this.dataForm.throwtime = data.storageGoods.throwtime
                this.dataForm.modifytime = data.storageGoods.modifytime
                this.dataForm.comment = data.storageGoods.comment
                this.dataForm.status = data.storageGoods.status
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
              url: this.$http.adornUrl(`/storage/storagegoods/${!this.dataForm.goodsid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'goodsid': this.dataForm.goodsid || undefined,
                // 'userid': this.dataForm.userid,
                'goodname': this.dataForm.goodname,
                'owername': this.dataForm.owername,
                'picture': this.dataForm.picture,
                'hotpointid': this.dataForm.hotpointid,
                'location': this.dataForm.location,
                'typeid': this.dataForm.typeid,
                'tag': this.dataForm.tag,
                'buytime': this.dataForm.buytime,
                'price': this.dataForm.price,
                'throwtime': this.dataForm.throwtime,
                'modifytime': this.dataForm.modifytime,
                'comment': this.dataForm.comment,
                'status': this.dataForm.status
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
