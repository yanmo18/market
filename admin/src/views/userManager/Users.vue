<template>
  <div>
    <!-- 搜索框 -->
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输用户名称" suffix-icon="el-icon-search" v-model="realname"></el-input>
      <el-button @click="load" class="ml-5" type="primary">搜索</el-button>
      <i>
        <el-button @click="reset" class="ml-5" type="warning">重置</el-button>
      </i>
    </div>
    <!-- 搜索框 -->

    <!-- 增加与删除 -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">
        新增
        <i class="el-icon-circle-plus-outline"></i>
      </el-button>

      <el-popconfirm class="ml-5" confirm-button-text="確定" cancel-button-text="我在想想" icon="el-icon-info"
        icon-color="red" title="您確定批量删除數據吗？" @confirm="delBatch">
        <el-button type="danger" slot="reference">
          批量删除
          <i class="el-icon-remove-outline"></i>
        </el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
      @selection-change="handleSelectionChange">
      >
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="ID" width="80"></el-table-column>

      <el-table-column width="150" align="center" prop="username" label="账号"></el-table-column>

      <el-table-column width="150" align="center" prop="realname" label="名称"></el-table-column>

      <el-table-column width="150" align="center" prop="age" label="年龄"></el-table-column>

      <el-table-column width="150" align="center" prop="sex" label="性别"></el-table-column>

      <el-table-column width="150" align="center" prop="money" label="账户余额（元）"></el-table-column>

      <el-table-column width="150" align="center" prop="phone" label="电话"></el-table-column>

      <el-table-column width="150" align="center" prop="address" label="地址"></el-table-column>

      <el-table-column width="150" align="center" prop="email" label="地址"></el-table-column>

      <el-table-column width="150" align="center" prop="photo" label="头像">
        <template slot-scope="scope">
          <img :src="scope.row.photo" alt style="width:95px;height:100px" />
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="success">
            编辑
            <i class="el-icon-edit"></i>
          </el-button>

          <!-- 刪除確認框 -->
          <el-popconfirm class="ml-5" confirm-button-text="確定" cancel-button-text="我在想想" icon="el-icon-info"
            icon-color="red" title="您確定删除吗？" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">
              删除
              <i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="padding: 10px 0">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
        :page-sizes="[2, 5, 10]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="100px" size="smail">
        <el-form-item label="账号">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>

        <!-- <el-form-item label="密码">
              <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>-->

        <el-form-item label="名称">
          <el-input v-model="form.realname" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="年龄">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-input v-model="form.sex" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="照片">
          <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload" :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img style="width:150px;height:150px" v-if="form.photo" :src="form.photo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <!-- <el-input v-model="form.photo" autocomplete="off"></el-input> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      password: "",
      realname: "",
      age: "",
      sex: "",
      phone: "",
      address: "",
      email: "",
      photo: "",
      dialogFormVisible: false, //默认不展示
      form: {},
      multipleSelection: [] //批量刪除
      // users: localStorage.getItem("users") ? JSON.parse(localStorage.getItem("users")) : {}
      // headerBg: "headerBg"
    };
  },
  created() {
    //请求分页查询数据
    this.load();
    // this.getUsers().then(res => {
    //   console.log(res)
    //   this.form = res
    // })
  },
  methods: {
    // async getUsers() {
    //   return (await this.request.get("/users/photo/" + this.users.photo)).data
    // },
    handleEdit(row) {
      //修改用户
      this.form = row;
      this.dialogFormVisible = true;
    },
    load() {
      this.request
        .get("/users/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            username: this.username,
            password: this.password,
            realname: this.realname,
            age: this.age,
            sex: this.sex,
            phone: this.phone,
            address: this.address,
            email: this.email,
            photo: this.photo
          }
        })
        .then(res => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
          // this.photo = res.data.photo;
        });
      //   fetch()
      //   .then(res => res.json()).then(res =>{
      //   this.tableData = res.data
      //   this.total = res.total
      //   console.log(res);
      // })
    },

    save() {
      this.request.post("/users", this.form).then(res => {
        if (res.code === "200") {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;

          this.load(); //刷新数据
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    handleAdd() {
      //增加用户
      this.dialogFormVisible = true;
      this.form = {};
    },

    del(id) {
      //删除用户
      this.request.delete("/users/" + id).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("删除失败");
        }
      });
    },

    handleSelectionChange(val) {
      //批量刪除
      console.log(val);
      this.multipleSelection = val;
    },
    //批量刪除
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id);
      this.request.post("/users/del/batch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    reset() {
      (this.realname = ""), this.load();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },

    handleAvatarSuccess(res) {
      this.form.photo = res;
    }
  }
};
</script>

<style>
.headerBg {
  background-color: #eee !important;
}
</style>