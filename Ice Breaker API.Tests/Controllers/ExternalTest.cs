using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Ice_Breaker_API.Controllers;

namespace Ice_Breaker_API.Tests.Controllers
{
    /// <summary>
    /// Summary description for ExternalTest
    /// </summary>
    [TestClass]
    public class ExternalTest
    {
        [TestMethod]
        public async void TestExternalGoogle()
        {
            var response = await External.Google("Cincinnati Ohio");
            Assert.IsNotNull(response);
        }
    }
}
