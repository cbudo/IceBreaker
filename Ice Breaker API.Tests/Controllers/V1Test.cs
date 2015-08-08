using System;
using Ice_Breaker_API.Controllers;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Ice_Breaker_API.Tests.Controllers
{
    [TestClass]
    public class V1Test
    {
        [TestMethod]
        public void TestMethod1()
        {
            V1Controller v1cont = new V1Controller();
            var jsonUserData = "{'email':'Soumya@gmail.com','name':'Soumya','token':'THISISME','photoURL':'thisimg.png'}";
            Assert.IsTrue(v1cont.Add(jsonUserData));
        }
    }
}
